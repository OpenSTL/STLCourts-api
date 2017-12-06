package svc.data.citations.datasources.transformers;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import svc.data.citations.datasources.CITATION_DATASOURCE;
import svc.data.jdbc.BaseJdbcDao;
import svc.logging.LogSystem;
import svc.models.Municipality;
import svc.types.HashableEntity;

@Component
public class MunicipalityIdTransformer extends BaseJdbcDao {

	public HashableEntity<Municipality> lookupMunicipalityId(CITATION_DATASOURCE datasource, String datasourceMunicipalityIdentifier) {
		if (datasource != null && datasourceMunicipalityIdentifier != null) {
			try {
				Map<String, Object> parameterMap = new HashMap<String, Object>();
				parameterMap.put("datasource", datasource.toString());
				parameterMap.put("datasourceMunicipalityIdentifier", datasourceMunicipalityIdentifier);
				Long municipalityId = jdbcTemplate.queryForObject(getSql("municipality/get-municipalityId-from-datasource.sql"), parameterMap, new MunicipalityIdSQLMapper());
				return new HashableEntity<Municipality>(Municipality.class, municipalityId);
			} catch (Exception e) {
				LogSystem.LogDBException(e);
				return null;
			}
		} else {
			return null;
		}
	}

	private class MunicipalityIdSQLMapper implements RowMapper<Long> {
		public Long mapRow(ResultSet rs, int i) {
			Long municipalityId;
			try {
				municipalityId = Long.parseLong(rs.getString("municipality_id"));
			} catch (Exception e) {
				LogSystem.LogDBException(e);
				return null;
			}

			return municipalityId;
		}
	}

}
