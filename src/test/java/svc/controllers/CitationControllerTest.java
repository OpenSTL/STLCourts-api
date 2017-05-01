package svc.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import svc.managers.CitationManager;

@RunWith(MockitoJUnitRunner.class)
public class CitationControllerTest {

	@InjectMocks
	CitationController controller;

	@Mock
	CitationManager managerMock;

	@Test
	public void placeholderTest() {
		return;
	}
}
