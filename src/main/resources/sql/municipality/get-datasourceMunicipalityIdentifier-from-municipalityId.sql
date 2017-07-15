SELECT dmm.datasource_municipality_identifier
FROM datasource_municipality_mapping dmm
INNER JOIN citation_datasource cd ON dmm.citation_datasource_id = cd.id
WHERE cd.name = :datasource