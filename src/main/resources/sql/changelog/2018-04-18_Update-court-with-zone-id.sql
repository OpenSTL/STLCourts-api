ALTER TABLE court ADD zone_id VARCHAR(100);

UPDATE court SET zone_id = 'America/Chicago' WHERE court_id > 0;

ALTER TABLE sms_alerts ADD zone_id VARCHAR(100) AFTER court_date;