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

import svc.hashids.Hashids;
import svc.managers.*;
import svc.models.*;


@RestController
@EnableAutoConfiguration
public class CourtController {	
	@Inject
	CourtManager courtManager;
	
	@Inject
	Hashids courtHashids;
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value="/courts")
	List<Court> GetCourts() {
		return courtManager.GetAllCourts();
	}
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value="/courts/{id}")
	Court GetCourt(@PathVariable("id") String idString) throws NotFoundException {
		long id = courtHashids.decode(idString)[0];
		Court court = courtManager.GetCourtById(id);
		if (court == null) {
			throw new NotFoundException("Court Not Found");
		}
		return court;
	}
	
	@ExceptionHandler(TypeMismatchException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason="Invalid Court ID")
	public void typeMismatchExceptionHandler(TypeMismatchException e, HttpServletResponse response){	
	}
	
	
}


