package svc.data.textMessages;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import svc.data.jdbc.BaseJdbcDao;
import svc.logging.LogSystem;
import svc.models.SMSAlert;
import svc.util.DatabaseUtilities;

@Repository
public class SMSAlertDAO extends BaseJdbcDao {	
	private boolean doesAlertExist(String citationNumber, String phoneNumber){
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("citationNumber", citationNumber);
		parameterMap.put("phoneNumber", phoneNumber);
		String sql = "SELECT * FROM sms_alerts WHERE citation_number = :citationNumber AND phone_number = :phoneNumber";
		try{
			jdbcTemplate.queryForObject(sql, parameterMap, new SMSAlertSQLMapper());
			return true;
		}catch(IncorrectResultSizeDataAccessException irsdae){
			return false;
		}catch(Exception e){
			LogSystem.LogDBException(e);
			return false;
		}
	}
	
	public boolean add(String citationNumber, LocalDateTime courtDateTime, String phoneNumber, LocalDate dob){
		if (!doesAlertExist(citationNumber,phoneNumber)){
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("citationNumber", citationNumber);
			parameterMap.put("courtDateTime", DatabaseUtilities.convertLocalDateTimeToDatabaseDate(courtDateTime));
			parameterMap.put("phoneNumber", phoneNumber);
			parameterMap.put("dob", DatabaseUtilities.convertLocalDateToDatabaseDate(dob));
			String sql = "INSERT INTO sms_alerts (citation_number,court_date,phone_number,date_of_birth) VALUES (:citationNumber,:courtDateTime,:phoneNumber,:dob)";
			try{
				int rowsAffected = jdbcTemplate.update(sql, parameterMap); 
				if (rowsAffected==1){
					return true;
				}else{
					return false;
				}
			}catch(Exception e){
				LogSystem.LogDBException(e);
				return false;
			}	
		}else{
			return true; //entry already exists, so return as if it were newly inserted
		}
	}
	
	public boolean remove(String citationNumber, String phoneNumber, LocalDate dob){
		if (doesAlertExist(citationNumber,phoneNumber)){
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("citationNumber", citationNumber);
			parameterMap.put("phoneNumber", phoneNumber);
			parameterMap.put("dob", DatabaseUtilities.convertLocalDateToDatabaseDate(dob));
			String sql = "DELETE FROM sms_alerts WHERE citation_number=:citationNumber  AND phone_number=:phoneNumber AND date_of_birth=:dob";
			try{
				jdbcTemplate.execute(sql, parameterMap, new PreparedStatementCallback<Boolean>(){

					@Override
					public Boolean doInPreparedStatement(PreparedStatement ps)
							throws SQLException, DataAccessException {
						return ps.execute();
					}
					
				});
				return true;
			}catch(Exception e){
				LogSystem.LogDBException(e);
				return false;
			}
		}else{
			return true;  //entry did not exist, to return as if it were removed
		}
	}
	
	public void removeExpiredAlerts(){
		LocalDateTime expiredDate = DatabaseUtilities.getCurrentDateTime();
		String sql = "DELETE FROM sms_alerts WHERE court_date < '"+DatabaseUtilities.convertLocalDateTimeToDatabaseDateString(expiredDate.plusDays(2))+"'";
		
		jdbcTemplate.execute(sql, new PreparedStatementCallback<Boolean>(){
			@Override
			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				return ps.execute();
			}
		});
			
	}
	
	public List<SMSAlert> getDailyAlerts(){
		LocalDateTime today = DatabaseUtilities.getCurrentDateTime();
		String sql = "SELECT * FROM sms_alerts";
		sql += " WHERE";
		sql += " (court_date < '"+DatabaseUtilities.convertLocalDateTimeToDatabaseDateString(today.plusDays(15))+"'";
		sql += " AND ";
		sql += "court_date > '"+DatabaseUtilities.convertLocalDateTimeToDatabaseDateString(today.plusDays(13))+"')";
		sql += " OR ";
		sql += " (court_date < '"+DatabaseUtilities.convertLocalDateTimeToDatabaseDateString(today.plusDays(8))+"'";
		sql += " AND ";
		sql += "court_date > '"+DatabaseUtilities.convertLocalDateTimeToDatabaseDateString(today.plusDays(6))+"')";
		sql += " OR ";
		sql += " (court_date < '"+DatabaseUtilities.convertLocalDateTimeToDatabaseDateString(today.plusDays(2))+"'";
		sql += " AND ";
		sql += "court_date > '"+DatabaseUtilities.convertLocalDateTimeToDatabaseDateString(today)+"')";
		
		
		try {
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			List<SMSAlert> smsAlerts = jdbcTemplate.query(sql, parameterMap, new SMSAlertSQLMapper());
			
			return smsAlerts;
		} catch (Exception e) {
			LogSystem.LogDBException(e);
			return null;
		}
	}
	
	public boolean updateSMSAlertWithUpdatedCourtDate(SMSAlert dailyAlert){
		String sql = "UPDATE sms_alerts SET court_date = :courtDate WHERE id = :smsAlertId";
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("courtDate", DatabaseUtilities.convertLocalDateTimeToDatabaseDate(dailyAlert.courtDate));
		parameterMap.put("smsAlertId", dailyAlert.id);
		try{
			jdbcTemplate.update(sql, parameterMap); 
		}catch(Exception e){
			LogSystem.LogDBException(e);
			return false;
		}
		return true;
	}
	
	private class SMSAlertSQLMapper implements RowMapper<SMSAlert> {
		public SMSAlert mapRow(ResultSet rs, int i) {
			SMSAlert smsAlert = new SMSAlert();
			try {	
				smsAlert.id = rs.getInt("id");
				smsAlert.citationNumber = rs.getString("citation_number");
				smsAlert.dob = DatabaseUtilities.getDatabaseLocalDate(rs.getDate("date_of_birth"));
				smsAlert.courtDate = DatabaseUtilities.getFromDatabase(rs, "court_date");
				smsAlert.defendantPhoneNumber = rs.getString("phone_number");
			} catch (Exception e) {
				LogSystem.LogDBException(e);
				return null;
			}
			
			return smsAlert;
		}
	}
}
