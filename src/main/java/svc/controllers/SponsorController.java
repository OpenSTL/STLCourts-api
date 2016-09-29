package svc.controllers;

import javax.inject.Inject;
import javax.security.sasl.AuthenticationException;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import svc.dto.Credentials;
import svc.logging.LogSystem;
import svc.managers.*;
import svc.models.*;

@RestController
@EnableAutoConfiguration
@RequestMapping("/sponsors")
public class SponsorController {	
	@Inject
	SponsorManager sponsorManager;
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value="/login")
	Sponsor Login(@RequestBody Credentials credentials) throws AuthenticationException {
		Sponsor sponsor = sponsorManager.Login(credentials.userId, credentials.password);
		if (sponsor == null) {
			throw new AuthenticationException();
		}
		
		return sponsor;
	}
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value="/{id}")
	Sponsor GetSponsor(@PathVariable("id") Long id) {
		if (id == null) {
			LogSystem.LogEvent("Null id passed to controller");
		}
		
		return sponsorManager.GetSponsorById(id);
	}
}
