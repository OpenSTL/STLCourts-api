DELETE sms_alerts FROM sms_alerts
LEFT OUTER JOIN citations c on sms_alerts.citation_number = c.citation_number
WHERE (DATEDIFF(CURDATE(),c.court_date) > 1)