CREATE TABLE citation_datasource (
  id      INTEGER         NOT NULL     AUTO_INCREMENT,
  name    VARCHAR(100)    NOT NULL,
  PRIMARY KEY (id)
)ENGINE=InnoDB;

CREATE TABLE citation_datasource_municipality (
  citation_datasource_id    INTEGER,
  municipality_id           INTEGER
)ENGINE=InnoDB;

ALTER TABLE citation_datasource_municipality ADD CONSTRAINT citation_datasource_municipality_fk_1 FOREIGN KEY (citation_datasource_id) REFERENCES citation_datasource (id);
ALTER TABLE citation_datasource_municipality ADD CONSTRAINT citation_datasource_municipality_fk_2 FOREIGN KEY (municipality_id) REFERENCES municipality (municipality_id);