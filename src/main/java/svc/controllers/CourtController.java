package svc.controllers;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.TypeMismatchException;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import svc.managers.*;
import svc.models.*;
import svc.security.HashUtil;

@RestController
@EnableAutoConfiguration
public class CourtController {	
	@Inject
	CourtManager courtManager;
	
	@Inject
	HashUtil hashUtil;
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value="/courts", produces="application/json")
	List<Court> GetCourts(HttpServletResponse response) {
		response.setHeader("Cache-Control", "public, max-age=86400, must-revalidate");
		return courtManager.getAllCourts();
	}
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value="/courts/{id}", produces="application/json")
	Court GetCourt(@PathVariable("id") String idString) throws NotFoundException {
		long id = hashUtil.decode(Court.class,idString);
		Court court = courtManager.getCourtById(id);
		if (court == null) {
			throw new NotFoundException("Court Not Found");
		}
		return court;
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value="municipalities/{municipalityId}/courts", produces="application/json")
	List<Court> GetCourtsByMunicipalityId(@PathVariable("municipalityId") String municipalityIdString) {
		long municipalityId = hashUtil.decode(Municipality.class,municipalityIdString);
		return courtManager.getCourtsByMunicipalityId(municipalityId);
	}
	
	@ExceptionHandler(TypeMismatchException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason="Invalid ID")
	public void typeMismatchExceptionHandler(TypeMismatchException e, HttpServletResponse response) {

	}
}


