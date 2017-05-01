package svc.data.textMessages;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.IncorrectResultSizeDataAccessException;
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
		try{
			jdbcTemplate.queryForObject(getSql("SMSAlert/check-for-existing-SMSAlert.sql"), parameterMap, new SMSAlertSQLMapper());
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
			try{
				int rowsAffected = jdbcTemplate.update(getSql("SMSAlert/insert-SMSAlert.sql"), parameterMap); 
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
			try{
				int rowsAffected = jdbcTemplate.update(getSql("SMSAlert/delete-SMSAlert.sql"), parameterMap); 
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
			return true;  //entry did not exist, to return as if it were removed
		}
	}
	
	public void removeExpiredAlerts(){
		LocalDateTime expiredDate = DatabaseUtilities.getCurrentDateTime().plusDays(2);
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("expiredDate", DatabaseUtilities.convertLocalDateTimeToDatabaseDateString(expiredDate));
		try{
			jdbcTemplate.update(getSql("SMSAlert/remove-expired-SMSAlerts.sql"), parameterMap); 
		}catch(Exception e){
			LogSystem.LogDBException(e);
		}
	}
	
	public List<SMSAlert> getDailyAlerts(){
		LocalDateTime today = DatabaseUtilities.getCurrentDateTime();
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("twoWeekPlus", DatabaseUtilities.convertLocalDateTimeToDatabaseDateString(today.plusDays(15)));
		parameterMap.put("twoWeekMinus", DatabaseUtilities.convertLocalDateTimeToDatabaseDateString(today.plusDays(13)));
		parameterMap.put("oneWeekPlus", DatabaseUtilities.convertLocalDateTimeToDatabaseDateString(today.plusDays(8)));
		parameterMap.put("oneWeekMinus", DatabaseUtilities.convertLocalDateTimeToDatabaseDateString(today.plusDays(6)));
		parameterMap.put("todayPlus", DatabaseUtilities.convertLocalDateTimeToDatabaseDateString(today.plusDays(2)));
		parameterMap.put("today", DatabaseUtilities.convertLocalDateTimeToDatabaseDateString(today));
		try {
			List<SMSAlert> smsAlerts = jdbcTemplate.query(getSql("SMSAlert/get-daily-SMSAlerts.sql"), parameterMap, new SMSAlertSQLMapper());
			return smsAlerts;
		} catch (Exception e) {
			LogSystem.LogDBException(e);
			return null;
		}
	}
	
	public List<SMSAlert> getDailyAlerts(String citationNumber, String phoneNumberToSendTo){
		LocalDateTime today = DatabaseUtilities.getCurrentDateTime();
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("twoWeekPlus", DatabaseUtilities.convertLocalDateTimeToDatabaseDateString(today.plusDays(15)));
		parameterMap.put("twoWeekMinus", DatabaseUtilities.convertLocalDateTimeToDatabaseDateString(today.plusDays(13)));
		parameterMap.put("oneWeekPlus", DatabaseUtilities.convertLocalDateTimeToDatabaseDateString(today.plusDays(8)));
		parameterMap.put("oneWeekMinus", DatabaseUtilities.convertLocalDateTimeToDatabaseDateString(today.plusDays(6)));
		parameterMap.put("todayPlus", DatabaseUtilities.convertLocalDateTimeToDatabaseDateString(today.plusDays(2)));
		parameterMap.put("today", DatabaseUtilities.convertLocalDateTimeToDatabaseDateString(today));
		parameterMap.put("citationNumber", citationNumber);
		parameterMap.put("phoneNumber", phoneNumberToSendTo);
		try {
			List<SMSAlert> smsAlerts = jdbcTemplate.query(getSql("SMSAlert/get-specific-daily-SMSAlerts.sql"), parameterMap, new SMSAlertSQLMapper());
			return smsAlerts;
		} catch (Exception e) {
			LogSystem.LogDBException(e);
			return null;
		}
	}
	
	public boolean updateSMSAlertWithUpdatedCourtDate(SMSAlert dailyAlert){
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("courtDate", DatabaseUtilities.convertLocalDateTimeToDatabaseDate(dailyAlert.courtDate));
		parameterMap.put("smsAlertId", dailyAlert.id);
		try{
			jdbcTemplate.update(getSql("SMSAlert/update-SMSAlert-courtDate.sql"), parameterMap); 
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
