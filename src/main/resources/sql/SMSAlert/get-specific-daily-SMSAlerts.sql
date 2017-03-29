SELECT * FROM sms_alerts WHERE 
((court_date < :twoWeekPlus AND court_date > :twoWeekMinus) 
OR (court_date < :oneWeekPlus AND court_date > :oneWeekMinus)
OR (court_date < :todayPlus AND court_date > :today))
AND citation_number = :citationNumber
AND phone_number = :phoneNumber