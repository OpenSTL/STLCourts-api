package svc.managers;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import svc.data.sponsors.SponsorDAO;
import svc.models.Sponsor;

@Component
public class SponsorManager {
	@Inject
	private SponsorDAO sponsorDAO;
	
	public Sponsor Login(String userId, String pwd) {
		return sponsorDAO.checkSponsorLogin(userId, pwd);
	}
	
	public Sponsor GetSponsorById(Long sponsorId) {
		return sponsorDAO.getBySponsorId(sponsorId);
	}
}
