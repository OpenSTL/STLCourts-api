DROP SCHEMA PUBLIC CASCADE;
/*http://hsqldb.org/doc/guide/compatibility-chapt.html#coc_compatibility_mysql*/

CREATE TABLE citations (
    id 						INTEGER  		IDENTITY PRIMARY KEY,
  citation_number 		      VARCHAR(100)     	NULL,
  citation_date 			      DATE     					NULL,
  first_name 				        VARCHAR(50)     	NULL,
  last_name 				        VARCHAR(50)     	NULL,
  date_of_birth 			      DATE     					NULL,
  defendant_address 		    VARCHAR(50)     	NULL,
  defendant_city 			      VARCHAR(50)    		NULL,
  defendant_state 		      VARCHAR(25)     	NULL,
  drivers_license_number 	  VARCHAR(25),
  drivers_license_state 	  VARCHAR(2),
  court_date 				        DATETIME     					NULL,
  court_id 				          INTEGER  					NULL,
  municipality_id 				          INTEGER  					NULL
);

CREATE TABLE court (
  court_id 						  INTEGER 					  NOT NULL,
  court_name				    VARCHAR(100),
  phone					        VARCHAR(50),
  extension				      VARCHAR(15),
  website					      VARCHAR(200),
 	payment_system			  VARCHAR(50),
  address 				      VARCHAR(50),
  city 					        VARCHAR(50),
  state 					      VARCHAR(25),
  zip_code 				      VARCHAR(12),
  latitude 				      DOUBLE PRECISION,
  longitude 				    DOUBLE PRECISION,
  citation_expires_after_days	SMALLINT
);

CREATE TABLE municipality (
    municipality_id 			  INTEGER 					NOT NULL,
    municipality_name		    VARCHAR(50),
    payment_url					VARCHAR(250)
);

CREATE TABLE municipality_court (
    municipality_id       INTEGER       NOT NULL,
    court_id              INTEGER       NOT NULL
);

CREATE TABLE judges (
  id 						INTEGER 					NOT NULL,
  judge		 			VARCHAR(100),
	court_id			INTEGER						NOT NULL
);

CREATE TABLE violations (
	id 						INTEGER 		IDENTITY PRIMARY KEY,
  citation_number 		    VARCHAR(100),
  violation_number 		    VARCHAR(100),
  violation_description 	VARCHAR(256),
  warrant_status 			    BOOLEAN 		    DEFAULT FALSE,
  warrant_number 			    VARCHAR(100),
  status 					        VARCHAR(100),
  status_date 			      TIMESTAMP,
  fine_amount 			      NUMERIC(15,2),
  court_cost 				      NUMERIC(15,2),
  can_pay_online			BOOLEAN	DEFAULT TRUE
);

CREATE TABLE sms_alerts
(
	id 						INTEGER 		IDENTITY PRIMARY KEY,
    citation_number 		VARCHAR(25),
    court_date 				DATETIME,
    phone_number			VARCHAR(25),
    date_of_birth 			DATE     						NULL
 );
 
CREATE TABLE citation_datasource (
  id      INTEGER         IDENTITY PRIMARY KEY,
  name    VARCHAR(100)    NOT NULL
);

CREATE TABLE citation_datasource_municipality (
  citation_datasource_id    INTEGER,
  municipality_id           INTEGER
);

CREATE TABLE tyler_court_mapping(
  court_id				INTEGER			NOT NULL,
  tyler_court_identifier VARCHAR(25)
);

CREATE TABLE datasource_municipality_mapping(
	citation_datasource_id	INTEGER,
	municipality_id			INTEGER,
	datasource_municipality_identifier VARCHAR(25)
);
