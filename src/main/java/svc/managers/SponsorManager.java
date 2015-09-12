package svc.managers;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import svc.data.SponsorDAO;
import svc.models.Sponsor;

@Component
public class SponsorManager
{
	@Inject
	private SponsorDAO _sponsorDAO;
	
	public Sponsor Login(String userId, String pwd)
	{
		return _sponsorDAO.checkSponsorLogin(userId, pwd);
	}
	
	public Sponsor GetSponsorById(int sponsorId)
	{
		return _sponsorDAO.getBySponsorId(sponsorId);
	}
}
