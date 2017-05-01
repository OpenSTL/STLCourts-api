SELECT cd.name
FROM citation_datasource_municipality cdm
  INNER JOIN citation_datasource cd ON cd.id = cdm.citation_datasource_id