ALTER TABLE court MODIFY court_name VARCHAR(100);

SET SQL_SAFE_UPDATES = 0;
UPDATE court SET court_name = CONCAT(court_name, ' Court');
SET SQL_SAFE_UPDATES = 1;

UPDATE court set court_name="St. Louis County Municipal Court - West Division" WHERE court_id=80;
UPDATE court set court_name="St. Louis County Municipal Court - Central Division" WHERE court_id=79;
UPDATE court set court_name="St. Louis County Municipal Court - North Division" WHERE court_id=51;
UPDATE court set court_name="St. Louis County Municipal Court - South Division" WHERE court_id=35;