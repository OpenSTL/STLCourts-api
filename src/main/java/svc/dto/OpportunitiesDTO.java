package svc.dto;

import java.util.List;

import svc.models.Opportunity;

public class OpportunitiesDTO 
{
	public OpportunitiesDTO(List<Opportunity> models)
	{
		this.opportunities = models;
	}
	
	public List<Opportunity> opportunities;
}
