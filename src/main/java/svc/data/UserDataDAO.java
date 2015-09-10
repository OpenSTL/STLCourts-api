package svc.data;

import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import svc.models.*;

import javax.sql.DataSource;

@Repository
public class UserDataDAO {
private static JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) { this.jdbcTemplate = new JdbcTemplate(dataSource); }
	
	public void addUserData(UserData userData) {
		try {
			String sql = "INSERT INTO users (userId, email) VALUES (?, ?)";
			jdbcTemplate.update(sql, userData.userId.toString(), userData.email);
			
		} catch (Exception e) {
		}
	}
	
	public void updateUserData(UserData userData) {
		try {
			String sql = "UPDATE users SET email = ? WHERE userId = ?";
			jdbcTemplate.update(sql, userData.email, userData.userId.toString());
		} catch (Exception e) {
			
		}
	}
	
	public UserData getUserDataById(BigInteger userId) {
		try {
			String sql = "SELECT * FROM users WHERE userId = ?";
			UserData userData = jdbcTemplate.queryForObject(sql, new UserDataMapper(), userId.toString());
			
			return userData;
		} catch (Exception e) {
			return null;
		}
	}
	
	public boolean checkForEmail(String email) {
		try {
			String sql = "SELECT count(*) FROM users WHERE email = ?";
			int count = 0;
			Object[] params = new Object[] { 
					   email
					};
			count = jdbcTemplate.queryForObject(sql, params, Integer.class);
			
			if (count >= 1) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
	
	public List<String> getEmailList(List<BigInteger> userIds) {
		List<String> emails = new ArrayList<String>();
		String sql = "";
		for (int i = 0; i < userIds.size(); i++) {
			try {
				sql = "SELECT * FROM users WHERE userId = ?";
				String id = userIds.get(i).toString();
				UserData userData = jdbcTemplate.queryForObject(sql, new UserDataMapper(), id);
				emails.add(userData.email);
			} catch (Exception e) {
			}
		}
		
		return emails;
	}
	
	private class UserDataMapper implements RowMapper<UserData> {
		public UserData mapRow(ResultSet rs, int i) throws SQLException {
			UserData userData = new UserData();
			try {
				userData.email = rs.getString("email");
				userData.userId = new BigInteger(rs.getString("userId"));
			} catch (Exception e) {
				return null;
			}
			
			return userData;
		}
	}
}
