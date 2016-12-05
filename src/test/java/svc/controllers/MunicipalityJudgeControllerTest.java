package svc.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;

import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MunicipalityJudgeControllerTest {

	@InjectMocks
	MunicipalityJudgeController controller;
	
	@Test (expected = UnsupportedOperationException.class)
	public void throwsExceptionWhenCalled(){
		controller.GetMunicipalityJudges();
	}
	
}
