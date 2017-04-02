INSERT INTO citation_datasource (id,name) VALUES (1,'TYLER');
INSERT INTO citation_datasource_municipality (citation_datasource_id,municipality_id) VALUES (1,90);

CREATE TABLE tyler_court_mapping(
  court_id				INTEGER			NOT NULL,
  tyler_court_identifier VARCHAR(25)
)ENGINE=InnoDB;

ALTER TABLE tyler_court_mapping ADD CONSTRAINT tyler_court_mapping_fk_1 FOREIGN KEY (court_id) REFERENCES courts (id);

INSERT INTO tyler_court_mapping (court_id,tyler_court_identifier) VALUES (35,'S');
INSERT INTO tyler_court_mapping (court_id,tyler_court_identifier) VALUES (51,'N');
INSERT INTO tyler_court_mapping (court_id,tyler_court_identifier) VALUES (79,'C');
INSERT INTO tyler_court_mapping (court_id,tyler_court_identifier) VALUES (80,'W');