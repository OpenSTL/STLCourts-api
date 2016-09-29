package svc.controllers;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;
import static org.hamcrest.CoreMatchers.*;

import svc.managers.CitationManager;
import svc.models.Citation;

@RunWith(MockitoJUnitRunner.class)
public class CitationControllerTest {

	@InjectMocks
	CitationController controller;
	
	@Mock
	CitationManager managerMock;
	
	@Test
	public void returnsTheCitationTheManagerGivesIt() {
		final Long CITATION_ID = 1L;
		final Citation CITATION = new Citation();
		
		when(managerMock.GetCitationById(CITATION_ID)).thenReturn(CITATION);
		
		Citation returnedCitation = controller.GetCitation(CITATION_ID);
		
		assertThat(returnedCitation, equalTo(CITATION));
	}

	@Test
	public void returnsNullWhenNotFound() {
		final Long CITATION_ID = 1L;
		
		when(managerMock.GetCitationById(CITATION_ID)).thenReturn(null);
		
		Citation returnedCitation = controller.GetCitation(CITATION_ID);
		
		assertThat(returnedCitation, is(nullValue()));
	}
}
