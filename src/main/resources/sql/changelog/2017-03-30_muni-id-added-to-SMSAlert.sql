DROP TABLE sms_alerts;

# Create new sms_alerts table
CREATE TABLE sms_alerts
(
	id 						      INTEGER 		        NOT NULL AUTO_INCREMENT PRIMARY KEY,
    citation_number 		VARCHAR(25),
    municipality_id			INTEGER 		NOT NULL,
    court_date 				DATETIME,
    phone_number			VARCHAR(25),
    date_of_birth 			DATE     						NULL
 )ENGINE=InnoDB;