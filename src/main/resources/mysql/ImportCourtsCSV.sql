TRUNCATE courts;

LOAD DATA INFILE '/Users/WJS0617/yourstlcourts/gh-spring-svc/scripts/hackathon-data/courts.csv' INTO TABLE courts
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 LINES
(id, latitude, longitude, municipality, address, city, state, zip_code)
