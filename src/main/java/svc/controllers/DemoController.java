package svc.controllers;

import java.util.List;

import javax.inject.Inject;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import svc.managers.*;
import svc.models.DemoCitation;

@ConditionalOnProperty("stlcourts.demoMode")
@RestController
@EnableAutoConfiguration
@RequestMapping("/Demo")
public class DemoController {	
	@Inject
	DemoManager demoManager;

	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value="/citations")
	List<DemoCitation> Citations() {
		return demoManager.createCitationsAndViolations();
	}

}
