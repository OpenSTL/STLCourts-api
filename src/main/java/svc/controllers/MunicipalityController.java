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
public class MunicipalityController {	
	@Inject
	MunicipalityManager municipalityManager;
	
	@Inject
	HashUtil hashUtil;
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value="/municipalities")
	List<Municipality> GetMunicipalities(HttpServletResponse response) {
		response.setHeader("Cache-Control", "public, max-age=86400, must-revalidate");
		return municipalityManager.GetAllMunicipalities();
	}
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value="/municipalities/{id}")
	Municipality GetMunicipality(@PathVariable("id") String idString) throws NotFoundException {
		long id = hashUtil.decode(Municipality.class,idString);
		Municipality municipality = municipalityManager.GetMunicipalityById(id);
		if (municipality == null) {
			throw new NotFoundException("Municipality Not Found");
		}
		return municipality;
	}
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value="courts/{courtId}/municipalities")
	List<Municipality> GetMunicipalityByCourtId(@PathVariable("courtId") String courtIdString) throws NotFoundException {
		long courtId = hashUtil.decode(Court.class, courtIdString);
		return municipalityManager.GetMunicipalitiesByCourtId(courtId);
	}
	
	@ExceptionHandler({TypeMismatchException.class,NumberFormatException.class})
	@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason="Invalid Municipality ID")
	public void typeMismatchExceptionHandler(TypeMismatchException e, HttpServletResponse response){	
	}
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value="/municipalities/supported")
	public List<Municipality> getAllMunicipalitiesSupportedByDataSource(){
		return municipalityManager.getAllMunicipalitiesSupportedByDataSource();
	}
}


