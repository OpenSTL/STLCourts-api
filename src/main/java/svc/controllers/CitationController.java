package svc.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import svc.dto.CitationSearchCriteria;
import svc.logging.LogSystem;
import svc.managers.*;
import svc.models.*;
import svc.security.HashUtil;

@RestController
@EnableAutoConfiguration
@RequestMapping("/citations")
public class CitationController {
	@Inject
	CitationManager citationManager;
	
	@Inject
	HashUtil hashUtil;

	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)
	//DOB parameter must be an ISO dateString of format 'yyyy-MM-dd'  no Time on end of string
	List<Citation> FindCitations(@RequestParam(value = "citationNumber", required = false) String citationNumber,
			                     @RequestParam(value = "licenseNumber", required = false) String licenseNumber,
			                     @RequestParam(value = "licenseState", required = false) String licenseState,
			                     @RequestParam(value = "firstName", required = false) String firstName,
			                     @RequestParam(value = "lastName", required = false) String lastName,
			                     @RequestParam(value = "municipalityIds", required = false) List<String> municipalityIds,
			                     @RequestParam(value = "dob", required = false) LocalDate dob) {
		CitationSearchCriteria criteria = new CitationSearchCriteria();
		if (citationNumber != null) {
			criteria.citationNumber = citationNumber;
		}

		if (dob != null) {
			criteria.dateOfBirth = dob;
		}

		if (licenseNumber != null && licenseState != null) {
			criteria.driversLicenseNumber = licenseNumber;
			criteria.driversLicenseState = licenseState;
		}

		if (lastName != null && municipalityIds != null && municipalityIds.size() != 0) {
			criteria.lastName = lastName;
			criteria.municipalities = new ArrayList<Long>();
			for(String municipalityId : municipalityIds){
				criteria.municipalities.add(hashUtil.decode(Municipality.class,municipalityId));
			}
		}

		if (firstName != null && lastName != null && licenseNumber != null) {
			criteria.firstName = firstName;
			criteria.lastName = lastName;
			criteria.driversLicenseNumber = licenseNumber;
		}
		
		return citationManager.findCitations(criteria);
	}
}