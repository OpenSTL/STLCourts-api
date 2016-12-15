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


@RestController
@EnableAutoConfiguration
public class MunicipalityController {	
	@Inject
	MunicipalityManager municipalityManager;
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value="/municipalities")
	List<Municipality> GetMunicipalities() {
		return municipalityManager.GetAllMunicipalities();
	}
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value="/municipalities/{id}")
	Municipality GetMunicipality(@PathVariable("id") Long id) throws NotFoundException {
		Municipality municipality = municipalityManager.GetMunicipalityById(id);
		if (municipality == null) {
			throw new NotFoundException("Municipality Not Found");
		}
		return municipality;
	}
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value="courts/{courtId}/municipalities")
	List<Municipality> GetMunicipalityByCourtId(@PathVariable("courtId") Long courtId) throws NotFoundException {
		return municipalityManager.GetMunicipalitiesByCourtId(courtId);
	}
	
	@ExceptionHandler({TypeMismatchException.class,NumberFormatException.class})
	@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason="Invalid Municipality ID")
	public void typeMismatchExceptionHandler(TypeMismatchException e, HttpServletResponse response){	
	}
	
	
}


