package svc.controllers;

import javax.inject.Inject;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import svc.dto.CourtsDTO;
import svc.logging.LogSystem;
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
	Court GetCourt(@PathVariable("id") Long id) {
		if (id == null) {
			LogSystem.LogEvent("Null id passed to controller");
		}
		
		return courtManager.GetCourtById(id);
	}
}
