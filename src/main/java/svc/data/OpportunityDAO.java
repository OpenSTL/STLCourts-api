package svc.data;

import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.ResultSet;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import svc.logging.LogSystem;
import svc.models.*;

import javax.sql.DataSource;

@Repository
public class OpportunityDAO
{
	private static JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) { OpportunityDAO.jdbcTemplate = new JdbcTemplate(dataSource); }
	
	public Opportunity getByOpportunityId(int opportunityId)
	{
		try 
		{
			String sql = "SELECT * FROM opportunities WHERE id = ?";
			Opportunity opportunity = jdbcTemplate.queryForObject(sql,
													 			  new OpportunitySQLMapper(),
													 			  opportunityId);
			return opportunity;
		}
		catch (Exception e)
		{
			LogSystem.LogDBException(e);
			return null;
		}
	}

	public List<Opportunity> LoadOpportunitiesForSponsor(Integer sponsorId) 
	{
		try 
		{
			String sql = "SELECT * FROM opportunities WHERE sponsor_id = ?";
			List<Opportunity> opportunities = jdbcTemplate.query(sql,
																 new OpportunitySQLMapper(),
																 sponsorId);
			return opportunities;
		}
		catch (Exception e)
		{
			LogSystem.LogDBException(e);
			return null;
		}
	}
	
	public Opportunity createOpportunity(Opportunity newOpportunity) 
	{
		newOpportunity.id = GetNextOpportunityId();
		
		String sql = "INSERT INTO opportunities (id, sponsor_id, name, short_description, full_description)" +
		             "VALUES (?, ?, ?, ?, ?)";
		try 
		{
			int affectedRows = jdbcTemplate.update(sql,
									 			  newOpportunity.id,
									 			  newOpportunity.sponsorId,
									 			  newOpportunity.name,
									 			  newOpportunity.shortDescription,
									 			  newOpportunity.fullDescription);
			if (affectedRows != 0)
			{
				return newOpportunity;
			}
		}
		catch (Exception e)
		{
			LogSystem.LogDBException(e);
			return null;
		}
		
		return null;
	}
	
	private int GetNextOpportunityId()
	{
		int nextId = 0;
		String sql = "SELECT MAX(id) FROM opportunities";
		Integer biggestId = jdbcTemplate.queryForObject(sql, Integer.class);
		if (biggestId != null)
		{
			nextId = biggestId + 1;
		}
		return nextId;
	}
	
	private class OpportunitySQLMapper implements RowMapper<Opportunity>
	{
		public Opportunity mapRow(ResultSet rs, int i)
		{
			Opportunity opportunity = new Opportunity();
			try
			{	
				opportunity.id = rs.getInt("id");
				opportunity.sponsorId = rs.getInt("sponsor_id");
				opportunity.name = rs.getString("name");
				opportunity.shortDescription = rs.getString("short_description");
				opportunity.fullDescription = rs.getString("full_description");
			}
			catch (Exception e)
			{
				LogSystem.LogDBException(e);
				return null;
			}
			
			return opportunity;
		}
	}
}
