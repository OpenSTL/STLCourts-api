TRUNCATE sponsors;

LOAD DATA INFILE '/Users/WJS0617/yourstlcourts/gh-spring-svc/scripts/hackathon-data/sponsors.csv' INTO TABLE sponsors
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 LINES
(id, name, short_description, contact_email, contact_phonenumber)