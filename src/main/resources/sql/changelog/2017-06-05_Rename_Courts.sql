ALTER TABLE court MODIFY court_name VARCHAR(100)
SELECT CONCAT(court_name, ' Court') court_name FROM court

UPDATE court set court_name="St. Louis County Municipal Court - West Division" WHERE court_id=80
UPDATE court set court_name="St. Louis County Municipal Court - Central Division" WHERE court_id=79
UPDATE court set court_name="St. Louis County Municipal Court - North Division" WHERE court_id=51
UPDATE court set court_name="St. Louis County Municipal Court - South Division" WHERE court_id=35