package svc.controllers;

import javax.inject.Inject;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import svc.dto.OpportunitiesDTO;
import svc.logging.LogSystem;
import svc.managers.*;
import svc.models.*;


@RestController
@EnableAutoConfiguration
@RequestMapping("inveo-api/opportunities")
public class OpportunityController
{	
	@Inject
	OpportunityManager _opportunityManager;
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)
	OpportunitiesDTO GetOpportunitiesForSponsor(@RequestParam("sponsorId") Integer sponsorId)
	{
		if (sponsorId == null)
		{
			LogSystem.LogEvent("Null id passed to controller");
		}
		
		return new OpportunitiesDTO(_opportunityManager.GetOpportunitiesForSponsor(sponsorId));
	}
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value="/{id}")
	Opportunity GetOpportunity(@PathVariable("id") Integer opportunityId)
	{
		if (opportunityId == null)
		{
			LogSystem.LogEvent("Null id passed to controller");
		}
		
		return _opportunityManager.getOpportunity(opportunityId);
	}
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	Opportunity CreateOpportunity(@RequestBody Opportunity newOpportunity)
	{
		if (newOpportunity == null)
		{
			LogSystem.LogEvent("Null opportunity passed to post.");
			return null;
		}
		
		if (newOpportunity.id != 0)
		{
			LogSystem.LogEvent("Opportunity with id was passed to post.");
			return null;
		}
		
		return _opportunityManager.createOpportunity(newOpportunity);
	}
}
