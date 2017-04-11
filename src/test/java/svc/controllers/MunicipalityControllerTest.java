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

import svc.managers.MunicipalityManager;
import svc.models.Court;
import svc.models.Municipality;
import svc.security.HashUtil;

@RunWith(MockitoJUnitRunner.class)
public class MunicipalityControllerTest {

	@InjectMocks
	MunicipalityController controller;
	
	@Mock
	HttpServletResponse response;
	
	HashUtil hashUtil = Mockito.mock(HashUtil.class);
	
	@Mock
	MunicipalityManager managerMock;
	@Test
	public void returnsAllMunicipalities(){
		final List<Municipality> MUNICIPALITIES = Arrays.asList(new Municipality[]{new Municipality()});

		when(managerMock.GetAllMunicipalities()).thenReturn(MUNICIPALITIES);
		List<Municipality> municipalities = controller.GetMunicipalities(response);
		verify(response).setHeader("Cache-Control", "public, max-age=86400, must-revalidate");
		assertThat(municipalities,equalTo(MUNICIPALITIES));
	}
	
	@Test
	public void returnsMunicipalityFromValidId() throws NotFoundException{
		final long MUNICIPALITY_ID = 1L;
		final String MUNICIPALITY_ID_STRING = "ABC";
		final Municipality MUNICIPALITY = new Municipality();
		
		when(hashUtil.decode(Municipality.class,MUNICIPALITY_ID_STRING)).thenReturn(MUNICIPALITY_ID);
		when(managerMock.GetMunicipalityById(MUNICIPALITY_ID)).thenReturn(MUNICIPALITY);
		Municipality returnedMunicipality = controller.GetMunicipality(MUNICIPALITY_ID_STRING);
		assertThat(returnedMunicipality,equalTo(MUNICIPALITY));
	}
	
	@Test (expected = NotFoundException.class)
	public void throwsExceptionWhenCourtNotFound() throws NotFoundException{
		final long MUNICIPALITY_ID = 1L;
		final String MUNICIPALITY_ID_STRING = "ABC";
		
		when(hashUtil.decode(Municipality.class,MUNICIPALITY_ID_STRING)).thenReturn(MUNICIPALITY_ID);
		when(managerMock.GetMunicipalityById(MUNICIPALITY_ID)).thenReturn(null);
		controller.GetMunicipality(MUNICIPALITY_ID_STRING);
	}
	
	@Test
	public void returnsMunicipalitiesFromValidCourtId() throws NotFoundException{
		final long COURT_ID = 2L;
		final String COURT_ID_STRING = "ABC";
		
		final Municipality MUNICIPALITY = new Municipality();
		final List<Municipality> MUNICIPALITIES = Arrays.asList(new Municipality[]{MUNICIPALITY});
		
		when(hashUtil.decode(Court.class,COURT_ID_STRING)).thenReturn(COURT_ID);
		when(managerMock.GetMunicipalitiesByCourtId(COURT_ID)).thenReturn(MUNICIPALITIES);
		
		List<Municipality> returnedMunicipalities = controller.GetMunicipalityByCourtId(COURT_ID_STRING);
		assertThat(returnedMunicipalities,equalTo(MUNICIPALITIES));
	}
	
	@Test
	public void returnsAllSupportedMunicipalities(){
		final Municipality MUNICIPALITY = new Municipality();
		final List<Municipality> MUNICIPALITIES = Arrays.asList(new Municipality[]{MUNICIPALITY});
		when(managerMock.getAllMunicipalitiesSupportedByDataSource()).thenReturn(MUNICIPALITIES);
		List<Municipality> municipalities = controller.getAllMunicipalitiesSupportedByDataSource();
		assertThat(municipalities,equalTo(MUNICIPALITIES));
	}
}
