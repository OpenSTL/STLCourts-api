SELECT municipality_id
FROM municipality_court
WHERE court_id = :courtId
LIMIT 1