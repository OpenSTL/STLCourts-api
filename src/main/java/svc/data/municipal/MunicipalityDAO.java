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
	public Municipality getByMunicipalityId(Long municipalityId){
		try{
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("municipalityId", municipalityId);
			String sql = "SELECT * FROM municipalities WHERE id = :municipalityId";
			Municipality municipality = jdbcTemplate.queryForObject(sql, parameterMap, new MunicipalitySQLMapper());
			return municipality;
		}catch (Exception e){
			return null;
		}
	}
	
	public List<Municipality> getAllMunicipalities() {
		try  {
			String sql = "SELECT * FROM municipalities";
			List<Municipality> municipalities = jdbcTemplate.query(sql, new MunicipalitySQLMapper());
			return municipalities;
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