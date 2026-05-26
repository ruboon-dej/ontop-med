-- Drugs table (maps to idmp:MedicinalProduct in Phase 2)
CREATE TABLE IF NOT EXISTS drugs (
    id          SERIAL PRIMARY KEY,
    drug_name   TEXT NOT NULL,
    atc_code    TEXT,
    form        TEXT,
    route       TEXT,
    created_at  TIMESTAMP DEFAULT now()
);

-- Substances (active ingredients)
CREATE TABLE IF NOT EXISTS substances (
    id              SERIAL PRIMARY KEY,
    substance_name  TEXT NOT NULL,
    cas_number      TEXT,
    inchi_key       TEXT
);

-- Drug-substance compositions
CREATE TABLE IF NOT EXISTS compositions (
    drug_id         INT REFERENCES drugs(id),
    substance_id    INT REFERENCES substances(id),
    strength        TEXT,
    PRIMARY KEY (drug_id, substance_id)
);

-- Drug-drug interactions
CREATE TABLE IF NOT EXISTS interactions (
    drug_a_id   INT REFERENCES drugs(id),
    drug_b_id   INT REFERENCES drugs(id),
    severity    TEXT,
    description TEXT,
    PRIMARY KEY (drug_a_id, drug_b_id)
);