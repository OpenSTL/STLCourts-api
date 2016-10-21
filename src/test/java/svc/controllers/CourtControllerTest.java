package svc.controllers;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.*;

import svc.dto.CourtsDTO;
import svc.managers.CourtManager;
import svc.models.Court;

@RunWith(MockitoJUnitRunner.class)
public class CourtControllerTest {

	@InjectMocks
	CourtController controller;
	
	@Mock
	CourtManager managerMock;
	@Test
	public void returnsAllCourts(){
		final List<Court> COURTS = Arrays.asList(new Court[]{new Court()});

		when(managerMock.GetAllCourts()).thenReturn(COURTS);
		CourtsDTO returnedCourtsDTO = controller.GetCourts();
		assertThat(returnedCourtsDTO.courts,equalTo(COURTS));
	}
	
	@Test
	public void returnsCourtFromValidId() throws NotFoundException{
		final long COURT_ID = 1L;
		final Court COURT = new Court();
		
		when(managerMock.GetCourtById(COURT_ID)).thenReturn(COURT);
		Court returnedCourt = controller.GetCourt(COURT_ID);
		assertThat(returnedCourt,equalTo(COURT));
	}
	
}
