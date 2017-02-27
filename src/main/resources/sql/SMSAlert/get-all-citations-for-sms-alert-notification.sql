SELECT c.citation_number,c.date_of_birth,c.court_date,c.court_id,ct.court_id,ct.court_name,ct.phone,ct.extension,ct.website,ct.address,ct.city,ct.state,ct.zip_code,al.citation_number,al.defendant_phone,al.date_of_birth, DATEDIFF(c.court_date,CURDATE()) AS DAYS_TILL_COURT
FROM citations c
INNER JOIN sms_alerts al ON al.citation_number = c.citation_number
LEFT OUTER JOIN court ct ON ct.court_id=c.court_id
WHERE (DAYS_TILL_COURT = 7) OR (DAYS_TILL_COURT = 1)