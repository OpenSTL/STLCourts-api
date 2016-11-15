package svc.data.municipal;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import svc.data.jdbc.BaseJdbcDao;
import svc.logging.LogSystem;
import svc.models.Municipality;

@Repository
public class MunicipalityDAO extends BaseJdbcDao {
	public Municipality getByCourtId(Long courtId){
		try{
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("courtId", courtId);
			String sql = "SELECT * FROM courts WHERE id = :courtId";
			Municipality municipality = jdbcTemplate.queryForObject(sql, parameterMap, new MunicipalitySQLMapper());
			return municipality;
		}catch (Exception e){
			return null;
		}
	}
	
	public List<Municipality> getAllCourts() {
		try  {
			String sql = "SELECT * FROM courts";
			List<Municipality> courts = jdbcTemplate.query(sql, new MunicipalitySQLMapper());
			return courts;
		} catch (Exception e) {
			LogSystem.LogDBException(e);
			return null;
		}
	}
	
	private class MunicipalitySQLMapper implements RowMapper<Municipality> {
		public Municipality mapRow(ResultSet rs, int i) {
			Municipality municipality = new Municipality();
			try {	
				municipality.id = rs.getInt("id");
				municipality.municipality = rs.getString("municipality");
				municipality.court_id = rs.getInt("court_id");
			} catch (Exception e) {
				LogSystem.LogDBException(e);
				return null;
			}
			
			return municipality;
		}
	}
}