package svc.data.textMessages;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
import svc.models.SMSAlertNotification;

@Repository
public class SMSAlertDAO extends BaseJdbcDao {	
	private boolean doesAlertExist(String citationNumber, String phoneNumber){
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("citationNumber", citationNumber);
		parameterMap.put("phoneNumber", phoneNumber);
		String sql = "SELECT * FROM sms_alerts WHERE citation_number = :citationId AND defendant_phone = :phoneNumber";
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
	
	public boolean add(String citationNumber, String phoneNumber, LocalDate dob){
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("citationNumber", citationNumber);
		parameterMap.put("phoneNumber", phoneNumber);
		parameterMap.put("dob", dob);
		//return jdbcTemplate.update("INSERT INTO SomeTable(column1, column2) VALUES(?,?)", someValue, someOtherValue)
		String sql = "INSERT INTO sms_alerts (citation_number,phone_number,date_of_birth) VALUES (:citationNumber,:phoneNumber,:dob) ON DUPLICATE KEY UPDATE citation_number = :citationNumber, defendant_phone = :phoneNumber, date_of_birth = :dob";
		try{
			int rowsAffected = jdbcTemplate.update(sql, parameterMap); 
			if ( rowsAffected==1 || rowsAffected==2){ //1 if inserted; 2 if updated
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			LogSystem.LogDBException(e);
			return false;
		}	
	}
	
	public boolean remove(String citationNumber, String phoneNumber, LocalDate dob){
		String sql = "DELETE FROM sms_alerts WHERE citation_number = ? AND defendant_phone = ? AND date_of_birth = ?";
		jdbcTemplate.execute(sql, new PreparedStatementCallback<Boolean>(){
			@Override
			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.setString(1, citationNumber);
				ps.setString(2, phoneNumber);
				ps.setObject(3, dob);
				
				return ps.execute();
			}
		});
		return true;
	}
	
	public void removeExpiredAlerts(){
		jdbcTemplate.execute(getSql("SMSAlert/delete-expired-sms-alert-notifications.sql"), new PreparedStatementCallback<Boolean>(){
			@Override
			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				return ps.execute();
			}
		});
			
	}
	
	public List<SMSAlertNotification> getDailyAlerts(){
		try  {
			SMSAlertNotificationRowCallbackHandler smsAlertNotificationRowCallbackHandler = new SMSAlertNotificationRowCallbackHandler();
			List<SMSAlertNotification> SMSAlertNotification = jdbcTemplate.query(getSql("SMSAlert/get-all-citations-for-sms-alert-notification.sql"), smsAlertNotificationRowCallbackHandler);

			return SMSAlertNotification;
		} catch (Exception e) {
			LogSystem.LogDBException(e);
			return null;
		}	
	}
	
	
	
	private class SMSAlertNotificationRowCallbackHandler implements RowMapper<SMSAlertNotification> {
		public SMSAlertNotification mapRow(ResultSet rs, int i) {
			SMSAlertNotification smsAlertNotification = new SMSAlertNotification();
			try {	
				smsAlertNotification.defendantPhone = rs.getString("defendant_phone");
				smsAlertNotification.citationNumber = rs.getString("citation_number");
				smsAlertNotification.dob = rs.getDate("date_of_birth").toLocalDate();
				smsAlertNotification.courtDate = rs.getDate("court_date").toLocalDate();
				smsAlertNotification.courtName = rs.getString("court_name");
				smsAlertNotification.courtPhone = rs.getString("phone");
				smsAlertNotification.courtPhoneExtension = rs.getString("extension");
				smsAlertNotification.courtWebsite = rs.getString("website");
				smsAlertNotification.courtAddress = rs.getString("address");
				smsAlertNotification.courtCity = rs.getString("city");
				smsAlertNotification.courtState = rs.getString("state");
				smsAlertNotification.courtZip = rs.getString("zip_code");
				smsAlertNotification.daysTillCourt = rs.getInt("DAYS_TILL_COURT");
			} catch (Exception e) {
				LogSystem.LogDBException(e);
				return null;
			}
			
			return smsAlertNotification;
		}
	}
	
	private class SMSAlertSQLMapper implements RowMapper<SMSAlert> {
		public SMSAlert mapRow(ResultSet rs, int i) {
			SMSAlert smsAlert = new SMSAlert();
			try {	
				smsAlert.id = rs.getInt("id");
				smsAlert.citationNumber = rs.getString("citation_number");
				smsAlert.dob = rs.getDate("date_of_birth").toLocalDate();
				smsAlert.defendant_phone = rs.getString("defendant_phone");
			} catch (Exception e) {
				LogSystem.LogDBException(e);
				return null;
			}
			
			return smsAlert;
		}
	}
}
