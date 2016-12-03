package svc.controllers;

import javax.inject.Inject;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import svc.managers.MunicipalityJudgeManager;
import svc.models.*;


@RestController
@EnableAutoConfiguration
@RequestMapping("/municipalityJudges")
public class MunicipalityJudgeController {
	@Inject
	MunicipalityJudgeManager municipalityJudgeManager;
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)
	MunicipalityJudge GetMunicipalityJudges() {
		throw new UnsupportedOperationException();
	}
}
