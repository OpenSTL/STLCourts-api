UPDATE municipality
SET payment_url="https://www.municipalonlinepayments.com/stlouiscountymo/court/search"
WHERE municipality_id = 86 OR municipality_id = 74 OR municipality_id = 51 OR municipality_id = 56 OR municipality_id = 38;

INSERT INTO citation_datasource_municipality (citation_datasource_id,municipality_id) VALUES (1,86);
INSERT INTO citation_datasource_municipality (citation_datasource_id,municipality_id) VALUES (1,74);
INSERT INTO citation_datasource_municipality (citation_datasource_id,municipality_id) VALUES (1,51);
INSERT INTO citation_datasource_municipality (citation_datasource_id,municipality_id) VALUES (1,56);
INSERT INTO citation_datasource_municipality (citation_datasource_id,municipality_id) VALUES (1,38);

UPDATE municipality_court
SET court_id=35
WHERE municipality_id = 51;

DELETE FROM citations WHERE court_id=47;
DELETE FROM court WHERE court_id=47;