package svc.data.sponsors;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import svc.data.jdbc.BaseJdbcDao;
import svc.logging.LogSystem;
import svc.models.Sponsor;

@Repository
public class SponsorDAO extends BaseJdbcDao {
	public Sponsor checkSponsorLogin(String userId, String pwd) {
		try {
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("userId", userId);
			parameterMap.put("pwd", pwd);
			String sql = "SELECT sponsors.* FROM sponsors INNER JOIN sponsor_login ON sponsors.id = sponsor_login.id " + 
					 "WHERE sponsor_login.userid = :userId AND sponsor_login.pwd = :pwd";
			return jdbcTemplate.queryForObject(sql, parameterMap, new SponsorSQLMapper());
		} catch (Exception e) {
			LogSystem.LogDBException(e);
			return null;
		}
	}
	
	public Sponsor getBySponsorId(Long sponsorId) {
		try  {
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("sponsorId", sponsorId);
			String sql = "SELECT * FROM sponsors WHERE id = :sponsorId";
			return jdbcTemplate.queryForObject(sql, parameterMap, new SponsorSQLMapper());
		} catch (Exception e) {
			LogSystem.LogDBException(e);
			return null;
		}
	}
	
	private class SponsorSQLMapper implements RowMapper<Sponsor> {
		public Sponsor mapRow(ResultSet rs, int i) {
			Sponsor sponsor = new Sponsor();
			try {	
				sponsor.id = rs.getLong("id");
				sponsor.name = rs.getString("name");
				sponsor.shortDescription = rs.getString("short_description");
				sponsor.contactEmail = rs.getString("contact_email");
				sponsor.contactPhoneNumber = rs.getString("contact_phonenumber");
			} catch (Exception e) {
				LogSystem.LogDBException(e);
				return null;
			}
			
			return sponsor;
		}
	}
}
