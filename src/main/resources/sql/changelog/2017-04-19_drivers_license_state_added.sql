ALTER TABLE citations
ADD drivers_license_state 	  VARCHAR(2);

UPDATE citations
SET drivers_license_state = "MO"
WHERE id > 0;