ALTER table court
ADD COLUMN citation_expires_after_days SMALLINT;


UPDATE court
SET citation_expires_after_days=-1
WHERE court_id IS NOT NULL;

UPDATE court
set citation_expires_after_days=8
WHERE court_id=35 OR court_id=51 OR court_id=79 OR court_id=80;