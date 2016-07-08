package svc.data.sponsors;

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
	public void setDataSource(DataSource municipalDataSource) { OpportunityDAO.jdbcTemplate = new JdbcTemplate(municipalDataSource); }
	
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
	
	public List<Opportunity> LoadOpportunitiesForCourt(int courtId) 
	{
		try 
		{
			String sql = "SELECT * FROM opportunities WHERE court_id = ?";
			List<Opportunity> opportunities = jdbcTemplate.query(sql,
																 new OpportunitySQLMapper(),
																 courtId);
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
		
		String sql = "INSERT INTO opportunities (id, sponsor_id, court_id, name, short_description, full_description)" +
		             "VALUES (?, ?, ?, ?, ?, ?)";
		try 
		{
			int affectedRows = jdbcTemplate.update(sql,
									 			  newOpportunity.id,
									 			  newOpportunity.sponsorId,
									 			  newOpportunity.courtId,
									 			  newOpportunity.name,
									 			  newOpportunity.shortDescription,
									 			  newOpportunity.fullDescription);
			if (affectedRows != 0)
			{
				return newOpportunity;
			}
			else
			{
				LogSystem.LogEvent("Unable to add opportunity.");
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
		int nextId = 1;
		String sql = "SELECT MAX(id) FROM opportunities";
		Integer biggestId = jdbcTemplate.queryForObject(sql, Integer.class);
		if (biggestId != null)
		{
			nextId = biggestId + 1;
		}
		return nextId;
	}
	
	public List<OpportunityNeed> getOpportunityNeedsForOpportunity(int opportunityId)
	{
		try 
		{
			String sql = "SELECT * FROM opportunity_needs WHERE opportunity_id = ?";
			List<OpportunityNeed> opportunityNeeds = jdbcTemplate.query(sql,
																        new OpportunityNeedSQLMapper(),
																        opportunityId);
			return opportunityNeeds;
		}
		catch (Exception e)
		{
			LogSystem.LogDBException(e);
			return null;
		}
	}
	
	public OpportunityNeed createOpportunityNeed(OpportunityNeed newOpportunityNeed)
	{
		newOpportunityNeed.id = GetNextOpportunityNeedId();
		
		String sql = "INSERT INTO opportunity_needs (id, opportunity_id, start_time, end_time, " +
				     " violation_fine_limit, desired_count, description)" +
		             "VALUES (?, ?, ?, ?, ?, ?, ?)";
		try 
		{
			int affectedRows = jdbcTemplate.update(sql,
									 			   newOpportunityNeed.id,
									 			   newOpportunityNeed.opportunityId,
									 			   newOpportunityNeed.startTime,
									 			   newOpportunityNeed.endTime,
									 			   newOpportunityNeed.violationFineLimit,
									 			   newOpportunityNeed.desiredCount,
									 			   newOpportunityNeed.description);
			if (affectedRows != 0)
			{
				return newOpportunityNeed;
			}
			else
			{
				LogSystem.LogEvent("Unable to add opportunity need.");
			}
		}
		catch (Exception e)
		{
			LogSystem.LogDBException(e);
			return null;
		}
		
		return null;
	}
	
	private int GetNextOpportunityNeedId()
	{
		int nextId = 1;
		String sql = "SELECT MAX(id) FROM opportunity_needs";
		Integer biggestId = jdbcTemplate.queryForObject(sql, Integer.class);
		if (biggestId != null)
		{
			nextId = biggestId + 1;
		}
		return nextId;
	}
	
	public List<OpportunityPairing> getOpportunityPairingsForNeed(int needId)
	{
		try 
		{
			String sql = "SELECT * FROM opportunity_need_pairings WHERE opportunity_need_id = ?";
			List<OpportunityPairing> opportunityPairings = jdbcTemplate.query(sql,
																        new OpportunityPairingSQLMapper(),
																        needId);
			return opportunityPairings;
		}
		catch (Exception e)
		{
			LogSystem.LogDBException(e);
			return null;
		}
	}
	
	public OpportunityPairing createOpportunityPairing(OpportunityPairing pairing)
	{
		pairing.id = GetNextOpportunityPairingId();
		String sql = "INSERT INTO opportunity_need_pairings (id, opportunity_need_id, violation_id, status) " +
	             	 "VALUES (?, ?, ?, ?)";
		try 
		{
			int affectedRows = jdbcTemplate.update(sql,
												   pairing.id,
												   pairing.opportunityNeedId,
												   pairing.violationId,
												   pairing.status);
			
			if (affectedRows != 0)
			{
				return pairing;
			}
			else
			{
				LogSystem.LogEvent("Unable to add opportunity need.");
			}
		}
		catch (Exception e)
		{
			LogSystem.LogDBException(e);
			return null;
		}
		
		return null;
	}
	
	private int GetNextOpportunityPairingId()
	{
		int nextId = 1;
		String sql = "SELECT MAX(id) FROM opportunity_need_pairings";
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
				opportunity.courtId = rs.getInt("court_id");
			}
			catch (Exception e)
			{
				LogSystem.LogDBException(e);
				return null;
			}
			
			return opportunity;
		}
	}
	
	private class OpportunityNeedSQLMapper implements RowMapper<OpportunityNeed>
	{
		public OpportunityNeed mapRow(ResultSet rs, int i)
		{
			OpportunityNeed opportunityNeed = new OpportunityNeed();
			try
			{	
				opportunityNeed.id = rs.getInt("id");
				opportunityNeed.opportunityId = rs.getInt("opportunity_id");
				opportunityNeed.startTime = rs.getTimestamp("start_time");
				opportunityNeed.endTime = rs.getTimestamp("end_time");
				opportunityNeed.violationFineLimit = rs.getBigDecimal("violation_fine_limit");
				opportunityNeed.desiredCount = rs.getInt("desired_count");
				opportunityNeed.description = rs.getString("description");
			}
			catch (Exception e)
			{
				LogSystem.LogDBException(e);
				return null;
			}
			
			return opportunityNeed;
		}
	}
	
	private class OpportunityPairingSQLMapper implements RowMapper<OpportunityPairing>
	{
		public OpportunityPairing mapRow(ResultSet rs, int i)
		{
			OpportunityPairing opportunityPairing = new OpportunityPairing();
			try
			{	
				opportunityPairing.id = rs.getInt("id");
				opportunityPairing.opportunityNeedId = rs.getInt("opportunity_need_id");
				opportunityPairing.violationId = rs.getInt("violation_id");
				opportunityPairing.status = rs.getString("status");
			}
			catch (Exception e)
			{
				LogSystem.LogDBException(e);
				return null;
			}
			
			return opportunityPairing;
		}
	}
}
