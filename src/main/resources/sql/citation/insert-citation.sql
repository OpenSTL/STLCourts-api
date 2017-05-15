INSERT INTO citations 
(citation_number,citation_date,first_name,last_name,date_of_birth,defendant_address,defendant_city,defendant_state,drivers_license_number,drivers_license_state,court_date,court_id)
VALUES 
(:citationNumber,:citationDate,:firstName,:lastName,:dob,:address,:city,:state,:driversLicenseNumber,:driversLicenseState,:courtDate,:courtId)