package svc.managers;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import svc.data.OpportunityDAO;
import svc.models.Opportunity;
import svc.models.OpportunityNeed;
import svc.models.OpportunityPairing;

@Component
public class OpportunityManager
{
	@Inject
	private OpportunityDAO _opportunityDAO;

	public List<Opportunity> GetOpportunitiesForSponsor(int sponsorId) 
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

	public OpportunityPairing createPairingForNeed(OpportunityPairing pairing)
	{
		return _opportunityDAO.createOpportunityPairing(pairing);
	}

	public List<Opportunity> GetOpportunitiesForCourt(int courtId)
	{
		return _opportunityDAO.LoadOpportunitiesForCourt(courtId);
	}

	public List<OpportunityNeed> getOpportunityNeedsForOpportunity(int opportunityId)
	{
		return _opportunityDAO.getOpportunityNeedsForOpportunity(opportunityId);
	}
}
