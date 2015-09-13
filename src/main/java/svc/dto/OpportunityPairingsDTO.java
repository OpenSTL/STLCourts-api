package svc.dto;

import java.util.List;

import svc.models.OpportunityPairing;

public class OpportunityPairingsDTO 
{
	public OpportunityPairingsDTO(List<OpportunityPairing> models)
	{
		this.opportunityPairings = models;
	}
	
	public List<OpportunityPairing> opportunityPairings;
}
