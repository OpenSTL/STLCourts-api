ALTER table court
ADD COLUMN court_type VARCHAR(25);

UPDATE court
SET court_type="Court"
WHERE court_id > 0;

UPDATE court
set court_type="Division"
WHERE court_id=35 OR court_id=51 OR court_id=79 OR court_id=80;