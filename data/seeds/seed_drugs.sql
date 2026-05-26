TRUNCATE drugs, substances, compositions, interactions RESTART IDENTITY CASCADE;
INSERT INTO drugs (drug_name, atc_code, form, route) VALUES
  ('Aspirin',       'B01AC06', 'tablet', 'oral'),
  ('Ibuprofen',     'M01AE01', 'tablet', 'oral'),
  ('Naproxen',      'M01AE02', 'tablet', 'oral'),
  ('Paracetamol',   'N02BE01', 'tablet', 'oral'),
  ('Metformin',     'A10BA02', 'tablet', 'oral');

INSERT INTO substances (substance_name) VALUES
  ('Acetylsalicylic acid'),
  ('Ibuprofen'),
  ('Naproxen sodium'),
  ('Paracetamol'),
  ('Metformin hydrochloride');