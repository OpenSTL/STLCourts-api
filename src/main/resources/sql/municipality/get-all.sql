SELECT *
FROM municipality m
INNER JOIN municipality_court mc ON mc.municipality_id = m.municipality_id
LEFT OUTER JOIN citation_datasource_municipality cdm ON cdm.municipality_id = m.municipality_id