SELECT c.court_id ,c.court_name, c.phone, c.website, c.extension, c.address, c.payment_system, c.city, c.state, c.zip_code, c.latitude, c.longitude, c.citation_expires_after_days, c.rights_type, c.rights_value, j.judge, j.id AS JUDGE_ID, j.court_id AS JUDGES_COURT_ID
FROM court c
INNER JOIN municipality_court mc ON mc.court_id = c.court_id
LEFT OUTER JOIN judges j ON j.court_id=c.court_id