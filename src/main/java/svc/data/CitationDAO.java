package svc.data;

import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.ResultSet;

import org.springframework.jdbc.core.RowMapper;

import svc.logging.LogSystem;
import svc.models.*;

import javax.sql.DataSource;

@Repository
public class CitationDAO
{
	private static JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) { CitationDAO.jdbcTemplate = new JdbcTemplate(dataSource); }
	
	public Citation getByCitationId(int citationId)
	{
		try 
		{
			String sql = "SELECT * FROM citations WHERE id = ?";
			Citation citation = jdbcTemplate.queryForObject(sql,
															new CitationSQLMapper(),
															citationId);
			
			return citation;
		}
		catch (Exception e)
		{
			LogSystem.LogDBException(e);
			return null;
		}
	}
	
	private class CitationSQLMapper implements RowMapper<Citation>
	{
		public Citation mapRow(ResultSet rs, int i)
		{
			Citation citation = new Citation();
			try
			{	
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
			}
			catch (Exception e)
			{
				LogSystem.LogDBException(e);
				return null;
			}
			
			return citation;
		}
	}
}
