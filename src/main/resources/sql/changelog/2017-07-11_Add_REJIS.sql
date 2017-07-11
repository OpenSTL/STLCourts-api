INSERT INTO citation_datasource (id,name) VALUES (2,'REJIS');

INSERT INTO municipality(municipality_id,municipality_name,payment_url) VALUES (92,'St. Louis City Municipal','');

INSERT INTO court(court_id,court_name,phone,extension,website,payment_system,address,city,state,zip_code,latitude,longitude,citation_expires_after_days) VALUES (81,'St. Louis City Municipal Court','314.622.3231','','http://www.stlcitycourt.org/frmHome.aspx','iPayCourt','1520 Market St.','St. Louis','MO','63103','38.627998','-90.203384',-1);

INSERT INTO municipality_court(municipality_id, court_id) VALUES (92, 81);

INSERT INTO citation_datasource_municipality (citation_datasource_id,municipality_id) VALUES (2,3); #Bellefontaine Neighbors
INSERT INTO citation_datasource_municipality (citation_datasource_id,municipality_id) VALUES (2,9); #Black Jack
INSERT INTO citation_datasource_municipality (citation_datasource_id,municipality_id) VALUES (2,11); #Brentwood
INSERT INTO citation_datasource_municipality (citation_datasource_id,municipality_id) VALUES (2,16); #Clarkson Valley
INSERT INTO citation_datasource_municipality (citation_datasource_id,municipality_id) VALUES (2,22); #Creve Coeur
INSERT INTO citation_datasource_municipality (citation_datasource_id,municipality_id) VALUES (2,25); #Des Peres
INSERT INTO citation_datasource_municipality (citation_datasource_id,municipality_id) VALUES (2,29); #Fenton
INSERT INTO citation_datasource_municipality (citation_datasource_id,municipality_id) VALUES (2,32); #Florissant
INSERT INTO citation_datasource_municipality (citation_datasource_id,municipality_id) VALUES (2,33); #Frontenac
INSERT INTO citation_datasource_municipality (citation_datasource_id,municipality_id) VALUES (2,40); #Hazelwood
INSERT INTO citation_datasource_municipality (citation_datasource_id,municipality_id) VALUES (2,44); #Kinloch
INSERT INTO citation_datasource_municipality (citation_datasource_id,municipality_id) VALUES (2,46); #Ladue
INSERT INTO citation_datasource_municipality (citation_datasource_id,municipality_id) VALUES (2,47); #Lakeshire
INSERT INTO citation_datasource_municipality (citation_datasource_id,municipality_id) VALUES (2,58); #Olivette
INSERT INTO citation_datasource_municipality (citation_datasource_id,municipality_id) VALUES (2,65); #Richmond Heights
INSERT INTO citation_datasource_municipality (citation_datasource_id,municipality_id) VALUES (2,66); #Riverview
INSERT INTO citation_datasource_municipality (citation_datasource_id,municipality_id) VALUES (2,71); #Sunset Hills
INSERT INTO citation_datasource_municipality (citation_datasource_id,municipality_id) VALUES (2,73); #Town and Country
INSERT INTO citation_datasource_municipality (citation_datasource_id,municipality_id) VALUES (2,75); #University City
INSERT INTO citation_datasource_municipality (citation_datasource_id,municipality_id) VALUES (2,88); #Winchester
INSERT INTO citation_datasource_municipality (citation_datasource_id,municipality_id) VALUES (2,89); #Woodson Terrace
INSERT INTO citation_datasource_municipality (citation_datasource_id,municipality_id) VALUES (2,12); #Bridgeton
INSERT INTO citation_datasource_municipality (citation_datasource_id,municipality_id) VALUES (2,43); #Jennings
INSERT INTO citation_datasource_municipality (citation_datasource_id,municipality_id) VALUES (2,45); #Kirkwood
INSERT INTO citation_datasource_municipality (citation_datasource_id,municipality_id) VALUES (2,92); #St. Louis City Municipal