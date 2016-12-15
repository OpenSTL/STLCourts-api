package svc.data.citations;

import java.sql.ResultSet;
import java.util.Date;
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
	
	public Citation getByCitationNumberAndDOB(String citationNumber, Date dob) {
		try {
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("citationNumber", citationNumber);
			parameterMap.put("dob", dob);
			String sql = "SELECT * FROM citations WHERE citation_number = :citationNumber AND date_of_birth = :dob";
			Citation citation = jdbcTemplate.queryForObject(sql, parameterMap, new CitationSQLMapper());
			
			return citation;
		} catch (Exception e) {
			LogSystem.LogDBException(e);
			return null;
		}
	}
	
	public List<Citation> getByDOBAndLicense(Date dob, String driversLicenseNumber) {
		try {
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("driversLicenseNumber", driversLicenseNumber);
			parameterMap.put("dob", dob);
			String sql = "SELECT * FROM citations WHERE date_of_birth = :dob AND drivers_license_number = :driversLicenseNumber";
			List<Citation> citations = jdbcTemplate.query(sql, parameterMap, new CitationSQLMapper());
			
			return citations;
		} catch (Exception e) {
			LogSystem.LogDBException(e);
			return null;
		}
	}
	
	public List<Citation> getByDOBAndNameAndMunicipalities(Date dob, String lastName, List<String> municipalities) {
		try {
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("lastName", lastName.toLowerCase());
			parameterMap.put("dob", new java.sql.Date(dob.getTime()).toString());
			municipalities.replaceAll(String::toLowerCase);
			parameterMap.put("municipalities", municipalities);
			
			String sql = "SELECT * FROM citations WHERE date_of_birth = :dob AND LOWER(last_name) = :lastName AND LOWER(court_location) IN (:municipalities)";
			List<Citation> citations = jdbcTemplate.query(sql, parameterMap, new CitationSQLMapper());
			
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
				citation.citation_date = rs.getDate("citation_date");
				citation.first_name = rs.getString("first_name");
				citation.last_name = rs.getString("last_name");
				citation.date_of_birth = rs.getDate("date_of_birth");
				citation.defendant_address = rs.getString("defendant_address");
				citation.defendant_city = rs.getString("defendant_city");
				citation.defendant_state = rs.getString("defendant_state");
				citation.drivers_license_number = rs.getString("drivers_license_number");
				citation.court_date = rs.getDate("court_date");
				citation.court_location = rs.getString("court_location");
				citation.court_address = rs.getString("court_address");
				citation.court_id = rs.getInt("court_id");
			} catch (Exception e) {
				LogSystem.LogDBException(e);
				return null;
			}
			
			return citation;
		}
	}
}
