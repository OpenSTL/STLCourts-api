TRUNCATE violations;

LOAD DATA INFILE '/Users/WJS0617/yourstlcourts/gh-spring-svc/scripts/hackathon-data/violations.csv' INTO TABLE violations
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 LINES
(id, citation_number, violation_number, violation_description, @warrant_status, warrant_number, status, status_date, @fine_amount, @court_cost)
SET warrant_status = STRCMP(@warrant_status, 'FALSE'),
	fine_amount = IF(char_length(trim(@fine_amount)) = 0, NULL, REPLACE(@fine_amount, '$', '')),
    court_cost = IF(char_length(trim(@court_cost)) = 0, NULL, REPLACE(@court_cost, '$', ''))