package svc.data.citations;

import java.sql.Date;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import svc.data.jdbc.BaseJdbcDao;
import svc.logging.LogSystem;
import svc.models.Citation;

@Repository
public class CitationDAO extends BaseJdbcDao {	
	public Citation getByCitationId(Long citationId) {
		try  {
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("citationId", citationId);
			String sql = "SELECT * FROM citations WHERE id = :citationId";
			Citation citation = jdbcTemplate.queryForObject(sql, parameterMap, new CitationSQLMapper());
			return citation;
		} catch (Exception e) {
			LogSystem.LogDBException(e);
			return null;
		}
	}
	
	public Citation getByCitationNumber(String citationNumber) {
		try {
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("citationNumber", citationNumber);
			String sql = "SELECT * FROM citations WHERE citation_number = :citationNumber";
			Citation citation = jdbcTemplate.queryForObject(sql, parameterMap, new CitationSQLMapper());
			return citation;
		} catch (Exception e) {
			LogSystem.LogDBException(e);
			return null;
		}
	}
	
	public Citation getByCitationNumberAndDOB(String citationNumber, LocalDate dob) {
		try {
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("citationNumber", citationNumber);
			parameterMap.put("dob", Date.valueOf(dob));
			String sql = "SELECT * FROM citations WHERE citation_number = :citationNumber AND date_of_birth = :dob";
			Citation citation = jdbcTemplate.queryForObject(sql, parameterMap, new CitationSQLMapper());
			
			return citation;
		} catch (Exception e) {
			LogSystem.LogDBException(e);
			return null;
		}
	}
	
	public List<Citation> getByDOBAndLicense(LocalDate dob, String driversLicenseNumber) {
		try {
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("driversLicenseNumber", driversLicenseNumber);
			parameterMap.put("dob", Date.valueOf(dob));
			String sql = "SELECT * FROM citations WHERE date_of_birth = :dob AND drivers_license_number = :driversLicenseNumber";
			List<Citation> citations = jdbcTemplate.query(sql, parameterMap, new CitationSQLMapper());
			
			return citations;
		} catch (Exception e) {
			LogSystem.LogDBException(e);
			return null;
		}
	}
	
	public List<Citation> getByDOBAndNameAndMunicipalities(LocalDate dob, String lastName, List<Long> municipalities) {
		try {
			Map<String, Object> parameterMap = new HashMap<>();
			parameterMap.put("lastName", lastName.toLowerCase());
			parameterMap.put("dob", Date.valueOf(dob));
			parameterMap.put("municipalities", municipalities);

			List<Citation> citations = jdbcTemplate.query(getSql("citation/get-by-location.sql"), parameterMap, new CitationSQLMapper());
			
			return citations;
		} catch (Exception e) {
			LogSystem.LogDBException(e);
			return null;
		}
	}
	
	private class CitationSQLMapper implements RowMapper<Citation> {
		public Citation mapRow(ResultSet rs, int i) {
			Citation citation = new Citation();
			try {	
				citation.id = rs.getInt("id");
				citation.citation_number = rs.getString("citation_number");
				citation.citation_date = (rs.getDate("citation_date")!=null)?rs.getDate("citation_date").toLocalDate():null;
				citation.first_name = rs.getString("first_name");
				citation.last_name = rs.getString("last_name");
				citation.date_of_birth = (rs.getDate("date_of_birth")!=null)?rs.getDate("date_of_birth").toLocalDate():null;
				citation.defendant_address = rs.getString("defendant_address");
				citation.defendant_city = rs.getString("defendant_city");
				citation.defendant_state = rs.getString("defendant_state");
				citation.drivers_license_number = rs.getString("drivers_license_number");
				LocalDate courtDate = (rs.getDate("court_date")!=null)?rs.getDate("court_date").toLocalDate():null;
				LocalTime courtTime = (rs.getTime("court_date")!=null)?rs.getTime("court_date").toLocalTime():null;
				if (courtDate==null || courtTime==null){
					citation.court_dateTime = null;
				}else{
					citation.court_dateTime = LocalDateTime.of(courtDate, courtTime);
				}
				citation.court_location = rs.getString("court_location");
				citation.court_address = rs.getString("court_address");
				citation.court_id = rs.getLong("court_id");
			} catch (Exception e) {
				LogSystem.LogDBException(e);
				return null;
			}
			
			return citation;
		}
	}
}
