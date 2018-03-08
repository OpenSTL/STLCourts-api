INSERT INTO citation_datasource (id,name) VALUES (3,'IMPORTEDITI');
INSERT INTO datasource_municipality_mapping (citation_datasource_id,municipality_id,datasource_municipality_identifier) VALUES (3,52,'MH');

INSERT INTO citation_datasource_municipality (citation_datasource_id,municipality_id) VALUES (3,52); /*Maryland Heights*/

CREATE TABLE imported_iti_court_mapping(
  court_id				INTEGER			NOT NULL,
  imported_iti_court_identifier VARCHAR(25)
)ENGINE=InnoDB;

ALTER TABLE imported_iti_court_mapping ADD CONSTRAINT imported_iti_court_mapping_fk_1 FOREIGN KEY (court_id) REFERENCES court (court_id);

INSERT INTO imported_court_mapping (court_id,imported_iti_court_identifier) VALUES (48,'MH'); /*Maryland Heights*/