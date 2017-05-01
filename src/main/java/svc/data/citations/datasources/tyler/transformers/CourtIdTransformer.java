package svc.data.citations.datasources.tyler.transformers;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import svc.data.jdbc.BaseJdbcDao;
import svc.logging.LogSystem;
import svc.models.Court;
import svc.types.HashableEntity;

@Component
public class CourtIdTransformer extends BaseJdbcDao {

	public HashableEntity<Court> lookupCourtId(String tylerCourtIdentifier) {
		if (tylerCourtIdentifier != null) {
			try {
				Map<String, Object> parameterMap = new HashMap<String, Object>();
				parameterMap.put("tylerCourtIdentifier", tylerCourtIdentifier);
				String sql = "SELECT court_id FROM tyler_court_mapping WHERE tyler_court_identifier = :tylerCourtIdentifier";
				Long courtId = jdbcTemplate.queryForObject(sql, parameterMap, new CourtIdSQLMapper());
				return new HashableEntity<Court>(Court.class, courtId);
			} catch (Exception e) {
				LogSystem.LogDBException(e);
				return null;
			}
		} else {
			return null;
		}
	}

	private class CourtIdSQLMapper implements RowMapper<Long> {
		public Long mapRow(ResultSet rs, int i) {
			Long courtId;
			try {
				courtId = Long.parseLong(rs.getString("court_id"));
			} catch (Exception e) {
				LogSystem.LogDBException(e);
				return null;
			}

			return courtId;
		}
	}

}
