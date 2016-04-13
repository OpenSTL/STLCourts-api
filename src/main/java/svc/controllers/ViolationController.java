package svc.controllers;

import javax.inject.Inject;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import svc.managers.ViolationManager;
import svc.models.*;


@RestController
@EnableAutoConfiguration
@RequestMapping("api/violations")
public class ViolationController {
	
	@Inject
	ViolationManager _violationManager;
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)
	Violation GetViolations()
	{
		return _violationManager.GetViolationById(1);
	}
}
