package svc.controllers;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import svc.data.*;
import svc.managers.*;
import svc.models.*;


@RestController
@EnableAutoConfiguration
@RequestMapping("inveo-api/citations")
public class CitationController {
	
	ViolationDAO _violationDAO = new ViolationDAO();
	CitationDAO _citationDAO = new CitationDAO();
	ViolationManager _violationManager = new ViolationManager(_violationDAO);
	CitationManager _citationManager = new CitationManager(_citationDAO, _violationManager);
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)
	Citation GetCitations()
	{
		return _citationManager.GetCitationById(1);
	}
}
