package svc.data.citations.datasources.mock;

import com.google.common.collect.Lists;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import svc.data.citations.CitationDataSource;
import svc.data.jdbc.BaseJdbcDao;
import svc.logging.LogSystem;
import svc.models.Citation;
import svc.models.Court;
import svc.types.HashableEntity;
import svc.util.DatabaseUtilities;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    public List<Citation> getByCitationNumberAndDOB(String citationNumber, LocalDate dob) {
        try {
            Map<String, Object> parameterMap = new HashMap<String, Object>();
            parameterMap.put("citationNumber", citationNumber);
            parameterMap.put("dob", Date.valueOf(dob));
            String sql = "SELECT * FROM citations WHERE citation_number = :citationNumber AND date_of_birth = :dob";
            Citation citation = jdbcTemplate.queryForObject(sql, parameterMap, new CitationSQLMapper());

            return Lists.newArrayList(citation);
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
 				String sql = "INSERT INTO citations (citation_number,citation_date,first_name,last_name,date_of_birth,defendant_address,defendant_city,defendant_state,drivers_license_number,court_date,court_location,court_address,court_id) VALUES ('"+c.citation_number+"','"+DatabaseUtilities.convertLocalDateToDatabaseDateString(c.citation_date)+"','"+c.first_name+"','"+c.last_name+"','"+DatabaseUtilities.convertLocalDateToDatabaseDateString(c.date_of_birth)+"','"+c.defendant_address+"','"+c.defendant_city+"','"+c.defendant_state+"','"+c.drivers_license_number+"','"+DatabaseUtilities.convertLocalDateTimeToDatabaseDateString(c.court_dateTime)+"','"+c.court_location+"','"+c.court_address+"',"+c.court_id.getValue()+")";
 				jdbcTemplate.execute(sql, new PreparedStatementCallback<Boolean>(){
					@Override
					public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
						return ps.execute();
					}
 				});
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
 				String sql = "DELETE FROM citations WHERE citation_number = '"+c.citation_number+"'";
 				jdbcTemplate.execute(sql, new PreparedStatementCallback<Boolean>(){
 					@Override
					public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
						return ps.execute();
					}
 				});
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
