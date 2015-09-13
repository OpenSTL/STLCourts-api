package svc.managers;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import svc.data.OpportunityDAO;
import svc.models.Opportunity;
import svc.models.OpportunityNeed;

@Component
public class OpportunityManager
{
	@Inject
	private OpportunityDAO _opportunityDAO;

	public List<Opportunity> GetOpportunitiesForSponsor(Integer sponsorId) 
	{
		return _opportunityDAO.LoadOpportunitiesForSponsor(sponsorId);
	}

	public Opportunity getOpportunity(Integer opportunityId) 
	{
		return _opportunityDAO.getByOpportunityId(opportunityId);
	}

	public Opportunity createOpportunity(Opportunity newOpportunity) 
	{
		return _opportunityDAO.createOpportunity(newOpportunity);
	}

	public OpportunityNeed addNeedToOpportunity(OpportunityNeed need) 
	{
		return _opportunityDAO.createOpportunityNeed(need);
	}
}
