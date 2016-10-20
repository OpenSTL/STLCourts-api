package svc.controllers;

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

import svc.dto.CourtsDTO;
import svc.managers.*;
import svc.models.*;


@RestController
@EnableAutoConfiguration
@RequestMapping("/courts")
public class CourtController {	
	@Inject
	CourtManager courtManager;
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)
	CourtsDTO GetCourts() {
		return new CourtsDTO(courtManager.GetAllCourts());
	}
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value="/{id}")
	Court GetCourt(@PathVariable("id") Long id) throws NotFoundException {
		Court court = courtManager.GetCourtById(id);
		return court;
	}
	
	@ExceptionHandler(TypeMismatchException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason="Invalid Court ID")
	public void typeMismatchExceptionHandler(TypeMismatchException e, HttpServletResponse response){
		
	}
	
}


