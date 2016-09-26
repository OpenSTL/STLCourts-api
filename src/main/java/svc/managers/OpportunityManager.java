package svc.managers;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import svc.data.sponsors.OpportunityDAO;
import svc.models.Opportunity;
import svc.models.OpportunityNeed;
import svc.models.OpportunityPairing;

@Component
public class OpportunityManager {
	@Inject
	private OpportunityDAO opportunityDAO;

	public List<Opportunity> GetOpportunitiesForSponsor(Long sponsorId) {
		return opportunityDAO.LoadOpportunitiesForSponsor(sponsorId);
	}

	public Opportunity getOpportunity(Long opportunityId) {
		return opportunityDAO.getByOpportunityId(opportunityId);
	}

	public Opportunity createOpportunity(Opportunity newOpportunity) {
		return opportunityDAO.createOpportunity(newOpportunity);
	}

	public OpportunityNeed addNeedToOpportunity(OpportunityNeed need) {
		return opportunityDAO.createOpportunityNeed(need);
	}

	public OpportunityPairing createPairingForNeed(OpportunityPairing pairing) {
		return opportunityDAO.createOpportunityPairing(pairing);
	}

	public List<Opportunity> GetOpportunitiesForCourt(Long courtId){
		return opportunityDAO.LoadOpportunitiesForCourt(courtId);
	}

	public List<OpportunityNeed> getOpportunityNeedsForOpportunity(Long opportunityId) {
		return opportunityDAO.getOpportunityNeedsForOpportunity(opportunityId);
	}

	public List<OpportunityPairing> getOpportunityPairingsForNeed(Long needId) {
		return opportunityDAO.getOpportunityPairingsForNeed(needId);
	}
}
