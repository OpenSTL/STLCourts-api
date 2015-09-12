package svc.data;

import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import svc.logging.LogSystem;
import svc.models.*;

import javax.sql.DataSource;

@Repository
public class ViolationDAO
{
	private static JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) { ViolationDAO.jdbcTemplate = new JdbcTemplate(dataSource); }
	
	public Violation getViolationDataById(int violationId)
	{
		try 
		{
			String sql = "SELECT * FROM violations WHERE id = ?";
			Violation violationData = jdbcTemplate.queryForObject(sql,
																  new ViolationSQLMapper(),
																  violationId);
			
			return violationData;
		}
		catch (Exception e)
		{
			LogSystem.LogDBException(e);
			return null;
		}
	}
	
	public List<Violation> getViolationsByCitationNumber(String citationNumber)
	{
		String sql = "SELECT * FROM violations WHERE citation_number = ?";
		List<Violation> violations = jdbcTemplate.query(sql,
														new ViolationSQLMapper(),
														citationNumber);
		return violations;
	}
	
	private class ViolationSQLMapper implements RowMapper<Violation> {
		public Violation mapRow(ResultSet rs, int i) throws SQLException {
			Violation violation = new Violation();
			try
			{	
				violation.id = rs.getInt("id");
				violation.citation_number = rs.getString("citation_number");
				violation.violation_number = rs.getString("violation_number");
				violation.violation_description = rs.getString("violation_description");
				violation.warrant_status = rs.getBoolean("warrant_status");
				violation.warrant_number = rs.getString("warrant_number");
				violation.status = rs.getString("status");
				violation.status_date = rs.getDate("status_date");
				violation.fine_amount = new BigDecimal(rs.getString("fine_amount").replace('$', ' ').trim());
				violation.court_cost = new BigDecimal(rs.getString("court_cost").replace('$', ' ').trim());
			}
			catch (Exception e)
			{
				LogSystem.LogDBException(e);
				return null;
			}
			
			return violation;
		}
	}
}
