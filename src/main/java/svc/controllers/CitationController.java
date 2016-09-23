package svc.controllers;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import svc.dto.CitationSearchCriteria;
import svc.dto.CitationsDTO;
import svc.logging.LogSystem;
import svc.managers.*;
import svc.models.*;

@RestController
@EnableAutoConfiguration
@RequestMapping("/citations")
public class CitationController
{	
	@Inject
	CitationManager _citationManager;
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value="/{id}")
	Citation GetCitation(@PathVariable("id") Integer id)
	{
		if (id == null)
		{
			LogSystem.LogEvent("Null id passed to controller");
		}
		
		return _citationManager.GetCitationById(id);
	}
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)
	CitationsDTO FindCitations(@RequestParam(value = "citationNumber", required = false) String citationNumber,
			                     @RequestParam(value = "licenseNumber", required = false) String licenseNumber,
			                     @RequestParam(value = "licenseState", required = false) String licenseState,
			                     @RequestParam(value = "firstName", required = false) String firstName,
			                     @RequestParam(value = "lastName", required = false) String lastName,
			                     @RequestParam(value = "municipalityNames", required = false) List<String> municipalityNames,
			                     @RequestParam(value = "dob", required = false) @DateTimeFormat(pattern="MM/dd/yyyy") Date dob)
	{
		CitationSearchCriteria criteria = new CitationSearchCriteria();
		if (citationNumber != null)
		{
			criteria.citation_number = citationNumber;
		}
		
		if (dob != null)
		{
			criteria.date_of_birth = dob;
		}
		
		if (licenseNumber != null && licenseState != null)
		{
			criteria.drivers_license_number = licenseNumber;
			criteria.drivers_license_state = licenseState;
		}
		
		if (lastName != null && municipalityNames != null && municipalityNames.size() != 0)
		{
			criteria.last_name = lastName;
			criteria.municipalities = municipalityNames;
		}
		if (firstName != null && lastName != null &&  licenseNumber != null)//for the text/phone system
		{
			criteria.first_name = firstName;
			criteria.last_name = lastName;
			criteria.drivers_license_number = licenseNumber;
		}
		return new CitationsDTO(_citationManager.FindCitations(criteria));
	}
}
