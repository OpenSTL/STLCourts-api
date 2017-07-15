package svc.data.citations.datasources.transformers;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import svc.data.citations.datasources.CITATION_DATASOURCE;
import svc.data.jdbc.BaseJdbcDao;
import svc.logging.LogSystem;
import svc.models.Court;
import svc.types.HashableEntity;

@Component
public class CourtIdTransformer extends BaseJdbcDao {

	public HashableEntity<Court> lookupCourtId(CITATION_DATASOURCE citationDataSource, String courtIdentifier) {
		if (courtIdentifier != null) {
			try {
				Map<String, Object> parameterMap = new HashMap<String, Object>();
				parameterMap.put("courtIdentifier", courtIdentifier);
				String sql = "";
				switch (citationDataSource){
				case REJIS:
					sql = this.getSql("citation/datasources/transformers/rejis-courtTransformer.sql");
					break;
				case TYLER:
					sql = this.getSql("citation/datasources/transformers/tyler-courtTransformer.sql");
					break;
				default:
					LogSystem.LogEvent("Unknown Citation Datasource for CourtIdTransformer");
					break;
				}
				
				if (sql != ""){
					Long courtId = jdbcTemplate.queryForObject(sql, parameterMap, new CourtIdSQLMapper());
					return new HashableEntity<Court>(Court.class, courtId);
				}else{
					return null;
				}
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
