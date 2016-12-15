DROP SCHEMA PUBLIC CASCADE
/*http://hsqldb.org/doc/guide/compatibility-chapt.html#coc_compatibility_mysql*/

CREATE TABLE citations 
(
    id 						INTEGER  		DEFAULT 0	NOT NULL,
    citation_number 		VARCHAR(100)     				NULL,
    citation_date 			DATE     						NULL,
    first_name 				VARCHAR(50)     				NULL,
    last_name 				VARCHAR(50)     				NULL,
    date_of_birth 			DATE     						NULL,
    defendant_address 		VARCHAR(50)     				NULL,
    defendant_city 			VARCHAR(50)    					NULL,
    defendant_state 		VARCHAR(25)     				NULL,
    drivers_license_number 	VARCHAR(25),
    court_date 				DATE     						NULL,
    court_location 			VARCHAR(50)     				NULL,
    court_address 			VARCHAR(50)     				NULL,
    court_id 				INTEGER  						NULL
);

CREATE TABLE courts 
(
    id 						INTEGER 					NOT NULL,
    court_name				VARCHAR(50),
    phone					VARCHAR(50),
    extension				VARCHAR(15),
    website					VARCHAR(200),
 	payment_system			VARCHAR(50),
    address 				VARCHAR(50),
    city 					VARCHAR(50),
    state 					VARCHAR(25),
    zip_code 				VARCHAR(12),
    latitude 				DOUBLE 			PRECISION,
    longitude 				DOUBLE 			PRECISION
);

CREATE TABLE municipalities 
(
    id 						INTEGER 					NOT NULL,
    municipality_name		VARCHAR(50),
	court_id				INTEGER						NOT NULL
);

CREATE TABLE judges 
(
    id 						INTEGER 					NOT NULL,
    judge		 			VARCHAR(100),
	court_id				INTEGER						NOT NULL
);

CREATE TABLE opportunities
(
    id 						INTEGER 					NOT NULL,
    sponsor_id 				INTEGER 					NOT NULL,
    name 					VARCHAR(100) 				NOT NULL,
    short_description 		VARCHAR(256) 				NOT NULL,
    full_description 		VARCHAR(2000),
    court_id 				INTEGER 					NOT NULL
);

CREATE TABLE opportunity_need_pairings
(
	id 						INTEGER,
    opportunity_need_id 	INTEGER,
    violation_id 			INTEGER,
    status 					VARCHAR(100)
);

CREATE TABLE opportunity_needs (
    id 						INTEGER 					NOT NULL,
    opportunity_id 			INTEGER,
    start_time 				DATETIME,
    end_time 				DATETIME,
    violation_fine_limit 	NUMERIC,
    desired_count 			INTEGER,
    description 			VARCHAR(500)
);

CREATE TABLE sponsor_login 
(
    id 						INTEGER 					NOT NULL,
    userid 					VARCHAR(30),
    pwd 					VARCHAR(30)
);

CREATE TABLE sponsors 
(
    id 						INTEGER 					NOT NULL,
    name 					VARCHAR(50),
    short_description 		VARCHAR(256),
    contact_email 			VARCHAR(50),
    contact_phonenumber 	VARCHAR(25)
);

CREATE TABLE violations
(
	id 						INTEGER 		DEFAULT 0	NOT NULL,
    citation_number 		VARCHAR(100),
    violation_number 		VARCHAR(100),
    violation_description 	VARCHAR(256),
    warrant_status 			BOOLEAN 		DEFAULT FALSE,
    warrant_number 			VARCHAR(100),
    status 					VARCHAR(100),
    status_date 			TIMESTAMP,
    fine_amount 			NUMERIC(15,2),
    court_cost 				NUMERIC(15,2)
);
