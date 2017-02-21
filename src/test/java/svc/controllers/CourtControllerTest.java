package svc.controllers;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.*;

import svc.hashids.Hashids;
import svc.managers.CourtManager;
import svc.models.Court;

@RunWith(MockitoJUnitRunner.class)
public class CourtControllerTest {

	@InjectMocks
	CourtController controller;
	
	Hashids courtHashids = Mockito.mock(Hashids.class);
	
	@Mock
	CourtManager managerMock;
	@Test
	public void returnsAllCourts(){
		final List<Court> COURTS = Arrays.asList(new Court[]{new Court()});

		when(managerMock.GetAllCourts()).thenReturn(COURTS);
		List<Court> courts = controller.GetCourts();
		assertThat(courts,equalTo(COURTS));
	}
	
	@Test
	public void returnsCourtFromValidId() throws NotFoundException{
		final long COURT_ID = 1L;
		final String COURT_ID_STRING = "ABC";
		final Court COURT = new Court();
		final long[] COURTS_ID = new long[]{COURT_ID};
		
		when(courtHashids.decode(COURT_ID_STRING)).thenReturn(COURTS_ID);
		when(managerMock.GetCourtById(COURT_ID)).thenReturn(COURT);
		Court returnedCourt = controller.GetCourt(COURT_ID_STRING);
		assertThat(returnedCourt,equalTo(COURT));
	}
	
	
	@Test (expected = NotFoundException.class)
	public void throwsExceptionWhenCourtNotFound() throws NotFoundException{
		final Long COURT_ID = 1L;
		final String COURT_ID_STRING = "ABC";
		final long[] COURTS_ID = new long[]{COURT_ID};
		
		when(courtHashids.decode(COURT_ID_STRING)).thenReturn(COURTS_ID);
		when(managerMock.GetCourtById(COURT_ID)).thenReturn(null);
		controller.GetCourt(COURT_ID_STRING);
	}
	
}
