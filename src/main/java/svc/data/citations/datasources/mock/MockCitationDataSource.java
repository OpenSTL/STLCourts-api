package svc.data.citations.datasources.mock;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import svc.data.citations.CitationDataSource;
import svc.data.citations.datasources.CITATION_DATASOURCE;
import svc.data.jdbc.BaseJdbcDao;
import svc.logging.LogSystem;
import svc.models.Citation;
import svc.models.Court;
import svc.types.HashableEntity;
import svc.util.DatabaseUtilities;

import java.sql.Date;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MockCitationDataSource extends BaseJdbcDao implements CitationDataSource {

	@Override
	public CITATION_DATASOURCE getCitationDataSource(){
		return CITATION_DATASOURCE.MOCK;
	}
	
    @Override
    public List<Citation> getByCitationNumberAndDOB(String citationNumber, LocalDate dob) {
        try {
            Map<String, Object> parameterMap = new HashMap<String, Object>();
            parameterMap.put("citationNumber", citationNumber);
            parameterMap.put("dob", Date.valueOf(dob));
            String sql = "SELECT * FROM citations WHERE citation_number = :citationNumber AND date_of_birth = :dob";
            List<Citation> citations = jdbcTemplate.query(sql, parameterMap, new CitationSQLMapper());

            return citations;
        } catch (Exception e) {
            LogSystem.LogDBException(e);
            return null;
        }
    }

    @Override
    public List<Citation> getByLicenseAndDOB(String driversLicenseNumber, LocalDate dob) {
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

    @Override
    public List<Citation> getByNameAndMunicipalitiesAndDOB(String lastName, List<Long> municipalities, LocalDate dob) {
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

    public boolean insertCitations(List<Citation> citations){
 		List<String> newCitationNumbers = new ArrayList<String>();
 		
 		try{
 			for(int i = 0; i < citations.size(); i++){
 				Citation c = citations.get(i);
 				Map<String, Object> parameterMap = new HashMap<>();
 	            parameterMap.put("citationNumber",c.citation_number);
 	            parameterMap.put("citationDate", DatabaseUtilities.convertLocalDateToDatabaseDateString(c.citation_date));
 	            parameterMap.put("firstName", c.first_name);
 	            parameterMap.put("lastName",c.last_name);
 	            parameterMap.put("dob", DatabaseUtilities.convertLocalDateToDatabaseDateString(c.date_of_birth));
 	            parameterMap.put("address", c.defendant_address);
 	            parameterMap.put("city", c.defendant_city);
 	            parameterMap.put("state", c.defendant_state);
 	            parameterMap.put("driversLicenseNumber", c.drivers_license_number);
 	            parameterMap.put("courtDate", DatabaseUtilities.convertLocalDateTimeToDatabaseDateString(c.court_dateTime));
 	            parameterMap.put("courtLocation", c.court_location);
 	            parameterMap.put("courtAddress", c.court_address);
 	            parameterMap.put("courtId", c.court_id.getValue());
 	            jdbcTemplate.update(getSql("citation/insert-citation.sql"), parameterMap); 
 	            newCitationNumbers.add(c.citation_number);
 			}
 		}catch(Exception e){
 			LogSystem.LogDBException(e);
 			return false;
 		}
 		return true;
 	}
		 	
 	public boolean removeCitations(List<Citation> citations){
 		try{
 			for(int i = 0; i < citations.size(); i++){
 				Citation c = citations.get(i);
 				Map<String, Object> parameterMap = new HashMap<>();
 	            parameterMap.put("citationNumber",c.citation_number);
 	            jdbcTemplate.update(getSql("citation/delete-citation-by-citationNumber.sql"), parameterMap); 
 			}
 		}catch(Exception e){
 			LogSystem.LogDBException(e);
 			return false;
 		}
 		return true;
 	}
 	
    private class CitationSQLMapper implements RowMapper<Citation> {
        public Citation mapRow(ResultSet rs, int i) {
            Citation citation = new Citation();
            try {
                citation.id = rs.getInt("id");
                citation.citation_number = rs.getString("citation_number");
                citation.citation_date = DatabaseUtilities.getDatabaseLocalDate(rs.getDate("citation_date"));
                citation.first_name = rs.getString("first_name");
                citation.last_name = rs.getString("last_name");
                citation.date_of_birth = DatabaseUtilities.getDatabaseLocalDate(rs.getDate("date_of_birth"));
                citation.defendant_address = rs.getString("defendant_address");
                citation.defendant_city = rs.getString("defendant_city");
                citation.defendant_state = rs.getString("defendant_state");
                citation.drivers_license_number = rs.getString("drivers_license_number");
                LocalDate courtDate = DatabaseUtilities.getDatabaseLocalDate(rs.getDate("court_date"));
                LocalTime courtTime = DatabaseUtilities.getDatabaseLocalTime(rs.getTime("court_date"));
                if (courtDate==null || courtTime==null){
                    citation.court_dateTime = null;
                }else{
                    citation.court_dateTime = LocalDateTime.of(courtDate, courtTime);
                }
                citation.court_location = rs.getString("court_location");
                citation.court_address = rs.getString("court_address");
                citation.court_id = new HashableEntity<Court>(Court.class,rs.getLong("court_id"));
            } catch (Exception e) {
                LogSystem.LogDBException(e);
                return null;
            }

            return citation;
        }
    }
}
