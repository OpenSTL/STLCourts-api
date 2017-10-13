DELETE FROM municipality_court WHERE municipality_id = 81 AND court_id = 72;
DELETE FROM court WHERE court_id=72;
DELETE FROM municipality WHERE municipality_id=81;
DELETE FROM judges WHERE court_id=72;