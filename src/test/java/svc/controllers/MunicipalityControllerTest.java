package svc.controllers;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.*;

import svc.dto.MunicipalitiesDTO;
import svc.managers.MunicipalityManager;
import svc.models.Municipality;

@RunWith(MockitoJUnitRunner.class)
public class MunicipalityControllerTest {

	@InjectMocks
	MunicipalityController controller;
	
	@Mock
	MunicipalityManager managerMock;
	@Test
	public void returnsAllMunicipalities(){
		final List<Municipality> MUNICIPALITIES = Arrays.asList(new Municipality[]{new Municipality()});

		when(managerMock.GetAllMunicipalities()).thenReturn(MUNICIPALITIES);
		MunicipalitiesDTO returnedMunicipalitiesDTO = controller.GetMunicipalities();
		assertThat(returnedMunicipalitiesDTO.municipalities,equalTo(MUNICIPALITIES));
	}
	
	@Test
	public void returnsMunicipalityFromValidId() throws NotFoundException{
		final long MUNICIPALITY_ID = 1L;
		final Municipality MUNICIPALITY = new Municipality();
		
		when(managerMock.GetMunicipalityById(MUNICIPALITY_ID)).thenReturn(MUNICIPALITY);
		Municipality returnedMunicipality = controller.GetMunicipality(MUNICIPALITY_ID);
		assertThat(returnedMunicipality,equalTo(MUNICIPALITY));
	}
	
	@Test (expected = NotFoundException.class)
	public void throwsExceptionWhenCourtNotFound() throws NotFoundException{
		final Long MUNICIPALITY_ID = 1L;
		
		when(managerMock.GetMunicipalityById(MUNICIPALITY_ID)).thenReturn(null);
		controller.GetMunicipality(MUNICIPALITY_ID);
	}
	
	@Test
	public void returnsMunicipalityFromValidCourtId() throws NotFoundException{
		final long COURT_ID = 2L;
		final Municipality MUNICIPALITY = new Municipality();
		
		when(managerMock.GetMunicipalityByCourtId(COURT_ID)).thenReturn(MUNICIPALITY);
		
		HashMap<String,String> params = new HashMap<>();
		params.put("courtId","2");
		
		Municipality returnedMunicipality = controller.GetMunicipalityByCourtId(params);
		assertThat(returnedMunicipality,equalTo(MUNICIPALITY));
	}
	
	@Test (expected = NumberFormatException.class)
	public void throwsExceptionWhenCourtIdIsNotLong() throws NotFoundException{
		HashMap<String,String> params = new HashMap<>();
		params.put("courtId","a");
		
		controller.GetMunicipalityByCourtId(params);
	}
	
}
