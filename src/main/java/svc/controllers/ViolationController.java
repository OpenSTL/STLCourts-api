package svc.controllers;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import svc.data.*;
import svc.managers.ViolationManager;
import svc.models.*;


@RestController
@EnableAutoConfiguration
@RequestMapping("inveo-api/violations")
public class ViolationController {
	
	ViolationDAO _dao = new ViolationDAO();
	ViolationManager _violationManager = new ViolationManager(_dao);
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)
	Violation GetViolations()
	{
		return _violationManager.GetViolationById(1);
	}
}
