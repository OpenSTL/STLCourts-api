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
import svc.managers.MunicipalityManager;
import svc.models.Municipality;

@RunWith(MockitoJUnitRunner.class)
public class MunicipalityControllerTest {

	@InjectMocks
	MunicipalityController controller;
	
	Hashids courtHashids = Mockito.mock(Hashids.class);
	Hashids municipalityHashids = Mockito.mock(Hashids.class);
	
	@Mock
	MunicipalityManager managerMock;
	@Test
	public void returnsAllMunicipalities(){
		final List<Municipality> MUNICIPALITIES = Arrays.asList(new Municipality[]{new Municipality()});

		when(managerMock.GetAllMunicipalities()).thenReturn(MUNICIPALITIES);
		List<Municipality> municipalities = controller.GetMunicipalities();
		assertThat(municipalities,equalTo(MUNICIPALITIES));
	}
	
	@Test
	public void returnsMunicipalityFromValidId() throws NotFoundException{
		final long MUNICIPALITY_ID = 1L;
		final String MUNICIPALITY_ID_STRING = "ABC";
		final Municipality MUNICIPALITY = new Municipality();
		final long[] MUNICIPALITIES_ID = new long[]{MUNICIPALITY_ID};
		
		when(municipalityHashids.decode(MUNICIPALITY_ID_STRING)).thenReturn(MUNICIPALITIES_ID);
		when(managerMock.GetMunicipalityById(MUNICIPALITY_ID)).thenReturn(MUNICIPALITY);
		Municipality returnedMunicipality = controller.GetMunicipality(MUNICIPALITY_ID_STRING);
		assertThat(returnedMunicipality,equalTo(MUNICIPALITY));
	}
	
	@Test (expected = NotFoundException.class)
	public void throwsExceptionWhenCourtNotFound() throws NotFoundException{
		final long MUNICIPALITY_ID = 1L;
		final String MUNICIPALITY_ID_STRING = "ABC";
		final long[] MUNICIPALITIES_ID = new long[]{MUNICIPALITY_ID};
		
		when(municipalityHashids.decode(MUNICIPALITY_ID_STRING)).thenReturn(MUNICIPALITIES_ID);
		when(managerMock.GetMunicipalityById(MUNICIPALITY_ID)).thenReturn(null);
		controller.GetMunicipality(MUNICIPALITY_ID_STRING);
	}
	
	@Test
	public void returnsMunicipalitiesFromValidCourtId() throws NotFoundException{
		final long COURT_ID = 2L;
		final String COURT_ID_STRING = "ABC";
		final long[] COURTS_ID = new long[]{COURT_ID};
		
		final Municipality MUNICIPALITY = new Municipality();
		final List<Municipality> MUNICIPALITIES = Arrays.asList(new Municipality[]{MUNICIPALITY});
		
		when(courtHashids.decode(COURT_ID_STRING)).thenReturn(COURTS_ID);
		when(managerMock.GetMunicipalitiesByCourtId(COURT_ID)).thenReturn(MUNICIPALITIES);
		
		List<Municipality> returnedMunicipalities = controller.GetMunicipalityByCourtId(COURT_ID_STRING);
		assertThat(returnedMunicipalities,equalTo(MUNICIPALITIES));
	}
	
}
