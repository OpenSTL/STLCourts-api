TRUNCATE opportunities;

LOAD DATA INFILE '/Users/WJS0617/yourstlcourts/gh-spring-svc/scripts/hackathon-data/opportunities.csv' INTO TABLE opportunities
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 LINES
(id, sponsor_id, name, short_description, full_description, court_id)
