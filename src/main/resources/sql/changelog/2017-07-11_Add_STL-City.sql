INSERT INTO municipality(municipality_id,municipality_name,payment_url) VALUES (92,'St. Louis City Municipal','');

INSERT INTO court(court_id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude,citation_expires_after_days) VALUES (81,'St. Louis City Municipal Court','314.622.3231','','http://www.stlcitycourt.org/frmHome.aspx','iPayCourt','1520 Market St.','St. Louis','MO','63103','38.627998','-90.203384',-1);

INSERT INTO municipality_court(municipality_id, court_id) VALUES (92, 81);