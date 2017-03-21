package svc.controllers;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import svc.dto.CitationSearchCriteria;
import svc.dto.CitationsDTO;
import svc.managers.CitationManager;

@RestController
@EnableAutoConfiguration
@RequestMapping("/citations")
public class CitationController {
	@Inject
	CitationManager citationManager;

	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)
	CitationsDTO FindCitations(@RequestParam(value = "citationNumber", required = false) String citationNumber,
			@RequestParam(value = "licenseNumber", required = false) String licenseNumber,
			@RequestParam(value = "licenseState", required = false) String licenseState,
			@RequestParam(value = "firstName", required = false) String firstName,
			@RequestParam(value = "lastName", required = false) String lastName,
			@RequestParam(value = "municipalityIds", required = false) List<Long> municipalityIds,
			@RequestParam(value = "dob", required = false) @DateTimeFormat(pattern = "MM/dd/yyyy") Date dob) {
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
			criteria.municipalities = municipalityIds;
		}

		// for the text system
		if (firstName != null && lastName != null && licenseNumber != null) {
			criteria.firstName = firstName;
			criteria.lastName = lastName;
			criteria.driversLicenseNumber = licenseNumber;
		}

		return new CitationsDTO(citationManager.findCitations(criteria));
	}
}
