DROP TABLE sms_alerts;

# Create new sms_alerts table
CREATE TABLE sms_alerts
(
	id 						      INTEGER 		        NOT NULL AUTO_INCREMENT PRIMARY KEY,
    citation_number 		VARCHAR(25),
    citation_data_source	VARCHAR(50),
    court_date 				DATETIME,
    phone_number			VARCHAR(25),
    date_of_birth 			DATE     						NULL
 )ENGINE=InnoDB;
