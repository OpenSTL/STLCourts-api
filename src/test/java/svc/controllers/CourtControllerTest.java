package svc.controllers;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.*;

import svc.managers.CourtManager;
import svc.models.Court;
import svc.models.Municipality;
import svc.security.HashUtil;

@RunWith(MockitoJUnitRunner.class)
public class CourtControllerTest {

	@InjectMocks
	CourtController controller;
	
	@Mock
	HttpServletResponse response;
	
	HashUtil hashUtil = Mockito.mock(HashUtil.class);
	
	@Mock
	CourtManager managerMock;
	@Test
	public void returnsAllCourts(){
		final List<Court> COURTS = Arrays.asList(new Court[]{new Court()});

		when(managerMock.getAllCourts()).thenReturn(COURTS);
		List<Court> courts = controller.GetCourts(response);
		verify(response).setHeader("Cache-Control", "public, max-age=86400, must-revalidate");
		assertThat(courts,equalTo(COURTS));
	}
	
	@Test
	public void returnsCourtFromValidId() throws NotFoundException{
		final long COURT_ID = 1L;
		final String COURT_ID_STRING = "ABC";
		final Court COURT = new Court();
		
		when(hashUtil.decode(Court.class,COURT_ID_STRING)).thenReturn(COURT_ID);
		when(managerMock.getCourtById(COURT_ID)).thenReturn(COURT);
		Court returnedCourt = controller.GetCourt(COURT_ID_STRING);
		assertThat(returnedCourt,equalTo(COURT));
	}
	
	@Test
	public void returnsCourtFromValidMunicipalityId(){
		final long MUNICIPALITY_ID = 1L;
		final String  MUNICIPALITY_ID_STRING = "ABC";
		final List<Court> COURTS = Arrays.asList(new Court[]{new Court()});
		
		when(hashUtil.decode(Municipality.class,MUNICIPALITY_ID_STRING)).thenReturn(MUNICIPALITY_ID);
		when(managerMock.getCourtsByMunicipalityId(MUNICIPALITY_ID)).thenReturn(COURTS);
		List<Court> returnedCourts = controller.GetCourtsByMunicipalityId(MUNICIPALITY_ID_STRING);
		assertThat(returnedCourts,equalTo(COURTS));
	}
	
	
	@Test (expected = NotFoundException.class)
	public void throwsExceptionWhenCourtNotFound() throws NotFoundException{
		final Long COURT_ID = 1L;
		final String COURT_ID_STRING = "ABC";
		
		when(hashUtil.decode(Court.class,COURT_ID_STRING)).thenReturn(COURT_ID);
		when(managerMock.getCourtById(COURT_ID)).thenReturn(null);
		controller.GetCourt(COURT_ID_STRING);
	}
	
}
