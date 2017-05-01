SELECT dmm.municipality_id
FROM citation_datasource cd
INNER JOIN datasource_municipality_mapping dmm ON dmm.citation_datasource_id = cd.id
WHERE cd.name = :datasource AND dmm.datasource_municipality_identifier = :datasourceMunicipalityIdentifier