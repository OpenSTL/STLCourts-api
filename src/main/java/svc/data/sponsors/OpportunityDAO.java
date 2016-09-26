package svc.data.sponsors;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import svc.data.jdbc.BaseJdbcDao;
import svc.logging.LogSystem;
import svc.models.Opportunity;
import svc.models.OpportunityNeed;
import svc.models.OpportunityPairing;

@Repository
public class OpportunityDAO extends BaseJdbcDao {
	private SimpleJdbcInsert opportunityInsert;
	private SimpleJdbcInsert opportunityNeedInsert;
	private SimpleJdbcInsert opportunityNeedPairingInsert;

    @Override
	protected void createSimpleJdbcInserts(DataSource dataSource) {
    	opportunityInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("opportunity_needs")
                .usingGeneratedKeyColumns("id")
                .usingColumns("sponsor_id", "court_id", "name", "short_description", "full_description");
    	
    	opportunityNeedInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("opportunities")
                .usingGeneratedKeyColumns("id")
                .usingColumns("opportunity_id", "start_time", "end_time", "violation_fine_limit", "desired_count", "description");
    	
    	opportunityNeedPairingInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("opportunity_need_pairings")
                .usingGeneratedKeyColumns("id")
                .usingColumns("opportunity_need_id", "violation_id", "status");
    }
    
	public Opportunity getByOpportunityId(Long opportunityId) {
		try  {
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("opportunityId", opportunityId);
			String sql = "SELECT * FROM opportunities WHERE id = :opportunityId";
			Opportunity opportunity = jdbcTemplate.queryForObject(sql, parameterMap, new OpportunitySQLMapper());
			return opportunity;
		} catch (Exception e) {
			LogSystem.LogDBException(e);
			return null;
		}
	}

	public List<Opportunity> LoadOpportunitiesForSponsor(Long sponsorId) {
		try {
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("sponsorId", sponsorId);
			String sql = "SELECT * FROM opportunities WHERE sponsor_id = :sponsorId";
			List<Opportunity> opportunities = jdbcTemplate.query(sql, new OpportunitySQLMapper());
			return opportunities;
		} catch (Exception e) {
			LogSystem.LogDBException(e);
			return null;
		}
	}
	
	public List<Opportunity> LoadOpportunitiesForCourt(Long courtId) {
		try {
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("courtId", courtId);
			String sql = "SELECT * FROM opportunities WHERE court_id = :courtId";
			List<Opportunity> opportunities = jdbcTemplate.query(sql, new OpportunitySQLMapper());
			return opportunities;
		} catch (Exception e) {
			LogSystem.LogDBException(e);
			return null;
		}
	}
	
	public Opportunity createOpportunity(Opportunity newOpportunity) {
		try {
			final SqlParameterSource parameterSource = new MapSqlParameterSource()
	                .addValue("sponsor_id", newOpportunity.sponsorId)
	                .addValue("court_id", newOpportunity.courtId)
	                .addValue("name", newOpportunity.name)
	                .addValue("short_description", newOpportunity.shortDescription)
	                .addValue("full_description", newOpportunity.fullDescription);
			
			Number key = opportunityInsert.executeAndReturnKey(parameterSource);
			newOpportunity.id = key.longValue();

			return newOpportunity;
		} catch (Exception e) {
			LogSystem.LogDBException(e);
			return null;
		}
	}
	
	public List<OpportunityNeed> getOpportunityNeedsForOpportunity(Long opportunityId){
		try {
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("opportunityId", opportunityId);
			String sql = "SELECT * FROM opportunity_needs WHERE opportunity_id = :opportunityId";
			List<OpportunityNeed> opportunityNeeds = jdbcTemplate.query(sql, parameterMap, new OpportunityNeedSQLMapper());
			return opportunityNeeds;
		} catch (Exception e) {
			LogSystem.LogDBException(e);
			return null;
		}
	}
	
	public OpportunityNeed createOpportunityNeed(OpportunityNeed newOpportunityNeed) {
		try {
			final SqlParameterSource parameterSource = new MapSqlParameterSource()
	                .addValue("opportunity_id", newOpportunityNeed.opportunityId)
	                .addValue("start_time", newOpportunityNeed.startTime)
	                .addValue("end_time", newOpportunityNeed.endTime)
	                .addValue("violation_fine_limit", newOpportunityNeed.violationFineLimit)
	                .addValue("desired_count", newOpportunityNeed.desiredCount)
	                .addValue("description", newOpportunityNeed.description);
			
			Number key = opportunityNeedInsert.executeAndReturnKey(parameterSource);
			newOpportunityNeed.id = key.longValue();

			return newOpportunityNeed;
		} catch (Exception e) {
			LogSystem.LogDBException(e);
			return null;
		}
	}
	
	public List<OpportunityPairing> getOpportunityPairingsForNeed(Long needId) {
		try  {
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("needId", needId);
			String sql = "SELECT * FROM opportunity_need_pairings WHERE opportunity_need_id = :needId";
			List<OpportunityPairing> opportunityPairings = jdbcTemplate.query(sql, parameterMap, new OpportunityPairingSQLMapper());
			return opportunityPairings;
		} catch (Exception e) {
			LogSystem.LogDBException(e);
			return null;
		}
	}
	
	public OpportunityPairing createOpportunityPairing(OpportunityPairing pairing) {
		try {
			final SqlParameterSource parameterSource = new MapSqlParameterSource()
	                .addValue("opportunity_need_id", pairing.opportunityNeedId)
	                .addValue("violation_id", pairing.violationId)
	                .addValue("status", pairing.status);
			
			Number key = opportunityNeedPairingInsert.executeAndReturnKey(parameterSource);
			pairing.id = key.longValue();
	
			return pairing;
		} catch (Exception e) {
			LogSystem.LogDBException(e);
			return null;
		}
	}
	
	private class OpportunitySQLMapper implements RowMapper<Opportunity> {
		public Opportunity mapRow(ResultSet rs, int i) {
			Opportunity opportunity = new Opportunity();
			try {	
				opportunity.id = rs.getLong("id");
				opportunity.sponsorId = rs.getInt("sponsor_id");
				opportunity.name = rs.getString("name");
				opportunity.shortDescription = rs.getString("short_description");
				opportunity.fullDescription = rs.getString("full_description");
				opportunity.courtId = rs.getInt("court_id");
			} catch (Exception e) {
				LogSystem.LogDBException(e);
				return null;
			}
			
			return opportunity;
		}
	}
	
	private class OpportunityNeedSQLMapper implements RowMapper<OpportunityNeed> {
		public OpportunityNeed mapRow(ResultSet rs, int i) {
			OpportunityNeed opportunityNeed = new OpportunityNeed();
			try {	
				opportunityNeed.id = rs.getLong("id");
				opportunityNeed.opportunityId = rs.getLong("opportunity_id");
				opportunityNeed.startTime = rs.getTimestamp("start_time");
				opportunityNeed.endTime = rs.getTimestamp("end_time");
				opportunityNeed.violationFineLimit = rs.getBigDecimal("violation_fine_limit");
				opportunityNeed.desiredCount = rs.getInt("desired_count");
				opportunityNeed.description = rs.getString("description");
			} catch (Exception e) {
				LogSystem.LogDBException(e);
				return null;
			}
			
			return opportunityNeed;
		}
	}
	
	private class OpportunityPairingSQLMapper implements RowMapper<OpportunityPairing> {
		public OpportunityPairing mapRow(ResultSet rs, int i) {
			OpportunityPairing opportunityPairing = new OpportunityPairing();
			try {	
				opportunityPairing.id = rs.getLong("id");
				opportunityPairing.opportunityNeedId = rs.getLong("opportunity_need_id");
				opportunityPairing.violationId = rs.getLong("violation_id");
				opportunityPairing.status = rs.getString("status");
			} catch (Exception e) {
				LogSystem.LogDBException(e);
				return null;
			}
			
			return opportunityPairing;
		}
	}
}
