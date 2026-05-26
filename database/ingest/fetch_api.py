import os, requests, psycopg2
from dotenv import load_dotenv

load_dotenv()

BASE = "https://api.fda.gov/drug/label.json"
params = {"search": "openfda.product_type:HUMAN+PRESCRIPTION+DRUG", "limit": 20}
data = requests.get(BASE, params=params).json()

conn = psycopg2.connect(
    dbname=os.getenv("POSTGRES_DB"),
    user=os.getenv("POSTGRES_USER"),
    password=os.getenv("POSTGRES_PASSWORD"),
    host="localhost", port=5432
)
with conn.cursor() as cur:
    for result in data.get("results", []):
        name = result.get("openfda", {}).get("brand_name", ["Unknown"])[0]
        cur.execute(
            "INSERT INTO drugs (drug_name) VALUES (%s) ON CONFLICT DO NOTHING",
            (name,)
        )
conn.commit()
conn.close()
print("Fetched from OpenFDA.")