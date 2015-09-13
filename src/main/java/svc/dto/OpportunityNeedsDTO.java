package svc.dto;

import java.util.List;

import svc.models.OpportunityNeed;

public class OpportunityNeedsDTO 
{
	public OpportunityNeedsDTO(List<OpportunityNeed> models)
	{
		this.opportunityNeeds = models;
	}
	
	public List<OpportunityNeed> opportunityNeeds;
}
