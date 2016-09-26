TRUNCATE opportunity_needs;

LOAD DATA INFILE '/Users/WJS0617/yourstlcourts/gh-spring-svc/scripts/hackathon-data/opportunity_needs.csv' INTO TABLE opportunity_needs
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 LINES
(id, opportunity_id, @start_time, @end_time, violation_fine_limit, desired_count, description)
SET start_time = STR_TO_DATE(@start_time, '%Y-%m-%d %H:%i:%s'),
    end_time = STR_TO_DATE(@end_time, '%Y-%m-%d %H:%i:%s')
