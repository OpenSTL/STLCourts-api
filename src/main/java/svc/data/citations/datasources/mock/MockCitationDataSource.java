package svc.data.citations.datasources.mock;

import com.google.common.collect.Lists;
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
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
