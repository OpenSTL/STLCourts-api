TRUNCATE citations;

LOAD DATA INFILE '/Users/WJS0617/yourstlcourts/pgexport/citations.csv' INTO TABLE citations
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 LINES
(id, citation_number, @citation_date, first_name, last_name, @date_of_birth, defendant_address, defendant_city, defendant_state, drivers_license_number,
 @court_date, court_location, court_address, @court_id)
SET citation_date = IF(char_length(trim(@citation_date)) = 0, NULL, @citation_date),
    date_of_birth = IF(char_length(trim(@date_of_birth)) = 0, NULL, @date_of_birth),
    court_date = IF(char_length(trim(@court_date)) = 0, NULL, @court_date),
    court_id = IF(char_length(trim(@court_id)) = 0, NULL, @court_id);
