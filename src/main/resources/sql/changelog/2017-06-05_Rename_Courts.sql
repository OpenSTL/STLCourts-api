ALTER TABLE court MODIFY court_name VARCHAR(100)
UPDATE court set court_name = cast ( court_name as nvarchar(50)) + cast (' Court' as nvarchar(50))

UPDATE court set court_name="St. Louis County Municipal Court - West Division" WHERE court_id=80
UPDATE court set court_name="St. Louis County Municipal Court - Central Division" WHERE court_id=79
UPDATE court set court_name="St. Louis County Municipal Court - North Division" WHERE court_id=51
UPDATE court set court_name="St. Louis County Municipal Court - South Division" WHERE court_id=35