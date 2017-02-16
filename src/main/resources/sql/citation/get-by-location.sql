SELECT *
FROM citations c
INNER JOIN municipality_court mc ON mc.court_id = c.court_id
WHERE c.date_of_birth = :dob
  AND LOWER(c.last_name) = :lastName
  AND mc.municipality_id IN (:municipalities)