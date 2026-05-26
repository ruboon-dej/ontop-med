import os, pandas as pd, psycopg2
from dotenv import load_dotenv

load_dotenv()

conn = psycopg2.connect(
    dbname=os.getenv("POSTGRES_DB"),
    user=os.getenv("POSTGRES_USER"),
    password=os.getenv("POSTGRES_PASSWORD"),
    host="localhost", port=5432
)

df = pd.read_csv("data/csv/drugs.csv")
with conn.cursor() as cur:
    for _, row in df.iterrows():
        cur.execute(
            "INSERT INTO drugs (drug_name, atc_code, form, route) VALUES (%s,%s,%s,%s) ON CONFLICT DO NOTHING",
            (row["drug_name"], row.get("atc_code"), row.get("form"), row.get("route"))
        )
conn.commit()
conn.close()
print("Loaded CSV.")