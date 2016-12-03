package svc.data.municipal;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import svc.data.jdbc.BaseJdbcDao;
import svc.logging.LogSystem;
import svc.models.Court;

@Repository
public class CourtDAO extends BaseJdbcDao {
	public Court getByCourtId(Long courtId){
		try{
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("courtId", courtId);
			String sql = "SELECT * FROM courts WHERE id = :courtId";
			Court court = jdbcTemplate.queryForObject(sql, parameterMap, new CourtSQLMapper());
			return court;
		}catch (Exception e){
			return null;
		}
	}
	
	public List<Court> getAllCourts() {
		try  {
			String sql = "SELECT * FROM courts";
			List<Court> courts = jdbcTemplate.query(sql, new CourtSQLMapper());
			return courts;
		} catch (Exception e) {
			LogSystem.LogDBException(e);
			return null;
		}
	}
	
	private class CourtSQLMapper implements RowMapper<Court> {
		public Court mapRow(ResultSet rs, int i) {
			Court court = new Court();
			try {	
				court.id = rs.getInt("id");
				court.court_name = rs.getString("court_name");
				court.phone = rs.getString("phone");
				court.extension = rs.getString("extension");
				court.website = rs.getString("website");
				court.payment_system = rs.getString("payment_system");
				court.address = rs.getString("address");
				court.city = rs.getString("city");
				court.state = rs.getString("state");
				court.zip_code = rs.getString("zip_code");
				court.latitude = new BigDecimal(rs.getString("latitude"));
				court.longitude = new BigDecimal(rs.getString("longitude"));
			} catch (Exception e) {
				LogSystem.LogDBException(e);
				return null;
			}
			
			return court;
		}
	}
}
