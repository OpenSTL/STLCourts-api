package svc.managers;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import svc.data.OpportunityDAO;

@Component
public class OpportunityManager
{
	@Inject
	private OpportunityDAO _opportunityDAO;

}
