package svc.data;

import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.ResultSet;
import java.util.Date;
import java.util.List;

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
	
	public Citation getByCitationNumber(String citation_number)
	{
		try 
		{
			String sql = "SELECT * FROM citations WHERE citation_number = ?";
			Citation citation = jdbcTemplate.queryForObject(sql,
															new CitationSQLMapper(),
															citation_number);
			
			return citation;
		}
		catch (Exception e)
		{
			LogSystem.LogDBException(e);
			return null;
		}
	}
	
	public List<Citation> getByDOBAndLicense(Date date_of_birth, String drivers_license_number)
	{
		try 
		{
			String sql = "SELECT * FROM citations WHERE date_of_birth = ? AND drivers_license_number = ?";
			List<Citation> citations = jdbcTemplate.query(sql,
												   		  new CitationSQLMapper(),
												   		  date_of_birth, 
												   		  drivers_license_number);
			
			return citations;
		}
		catch (Exception e)
		{
			LogSystem.LogDBException(e);
			return null;
		}
	}
	
	public List<Citation> getByDOBAndNameAndMunicipalities(Date date_of_birth,
														   String first_name,
														   String last_name,
														   List<String> municipalities)
	{
		try 
		{
			String sql = "SELECT * FROM citations WHERE date_of_birth = \'" +
			             new java.sql.Date(date_of_birth.getTime()).toString() + "\' AND LOWER(first_name) = \'" +
					     first_name.toLowerCase() + "\' AND LOWER(last_name) = \'" + 
			             last_name.toLowerCase() + "\' AND LOWER(court_location) IN (";
			for (String municipality:municipalities)
			{
				sql += "\'" + municipality.toLowerCase() + "\',";
			}
			sql = sql.substring(0, sql.length() - 1);
			sql += ")";
			List<Citation> citations = jdbcTemplate.query(sql,
												   		  new CitationSQLMapper());
			
			return citations;
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
				citation.court_id = rs.getInt("court_id");
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
