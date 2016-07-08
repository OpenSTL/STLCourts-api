package svc.data.sponsors;

import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.ResultSet;

import org.springframework.jdbc.core.RowMapper;

import svc.logging.LogSystem;
import svc.models.*;

import javax.sql.DataSource;

@Repository
public class SponsorDAO
{
	private static JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource municipalDataSource) { SponsorDAO.jdbcTemplate = new JdbcTemplate(municipalDataSource); }
	
	public Sponsor checkSponsorLogin(String userId, String pwd)
	{
		String sql = "SELECT sponsors.* FROM sponsors INNER JOIN sponsor_login ON sponsors.id = sponsor_login.id " + 
					 "WHERE sponsor_login.userid = ? AND sponsor_login.pwd = ?";
		try 
		{
			Sponsor sponsor = jdbcTemplate.queryForObject(sql,
													 new SponsorSQLMapper(),
													 userId, pwd);
			return sponsor;
		}
		catch (Exception e)
		{
			LogSystem.LogDBException(e);
			return null;
		}
	}
	
	public Sponsor getBySponsorId(int sponsorId)
	{
		try 
		{
			String sql = "SELECT * FROM sponsors WHERE id = ?";
			Sponsor sponsor = jdbcTemplate.queryForObject(sql,
													 new SponsorSQLMapper(),
													 sponsorId);
			
			return sponsor;
		}
		catch (Exception e)
		{
			LogSystem.LogDBException(e);
			return null;
		}
	}
	
	private class SponsorSQLMapper implements RowMapper<Sponsor>
	{
		public Sponsor mapRow(ResultSet rs, int i)
		{
			Sponsor sponsor = new Sponsor();
			try
			{	
				sponsor.id = rs.getInt("id");
				sponsor.name = rs.getString("name");
				sponsor.shortDescription = rs.getString("short_description");
				sponsor.contactEmail = rs.getString("contact_email");
				sponsor.contactPhoneNumber = rs.getString("contact_phonenumber");
			}
			catch (Exception e)
			{
				LogSystem.LogDBException(e);
				return null;
			}
			
			return sponsor;
		}
	}
}
