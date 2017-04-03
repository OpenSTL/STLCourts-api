DROP TABLE opportunities;
DROP TABLE opportunity_need_pairings;
DROP TABLE opportunity_needs;
DROP TABLE sponsor_login;
DROP TABLE sponsors;

ALTER TABLE municipalities ADD payment_url VARCHAR(250);

CREATE TABLE datasource_municipality_mapping(
	citation_datasource_id	INTEGER,
	municipality_id			INTEGER,
	datasource_municipality_identifier VARCHAR(25)
)ENGINE=InnoDB;

ALTER TABLE datasource_municipality_mapping ADD CONSTRAINT datasource_municipality_mapping_fk_1 FOREIGN KEY (municipality_id) REFERENCES municipality (municipality_id);

INSERT INTO datasource_municipality_mapping (citation_datasource_id,municipality_id,datasource_municipality_identifier) VALUES (1,90,'County');

DROP TABLE municipalities;

INSERT INTO municipality_court(municipality_id, court_id) VALUES (91, 48);
