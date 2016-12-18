package svc.data.municipal;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;

import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import static org.hamcrest.CoreMatchers.*;

import svc.managers.MunicipalityManager;
import svc.models.Municipality;

@RunWith(MockitoJUnitRunner.class)
public class MunicipalityDAOTest{
	@InjectMocks
	MunicipalityDAO municipalityDAO;
	
	@Mock
	MunicipalityManager managerMock;
	
	@Mock
	NamedParameterJdbcTemplate jdbcTemplate;
		
	@SuppressWarnings("unchecked")
	@Test
	public void returnsMunicipalitiesFromCourtId(){
		final Municipality MUNICIPALITY = new Municipality();
		MUNICIPALITY.municipality_name = "myMuni";
		final List<Municipality> MUNICIPALITIES = Arrays.asList(MUNICIPALITY);
		final long COURTID = 123458L;
		when(jdbcTemplate.query(Matchers.anyString(), Matchers.anyMap(), Matchers.<RowMapper<Municipality>>any()))
        .thenReturn(MUNICIPALITIES);

		List<Municipality> municipalities = municipalityDAO.getByCourtId(COURTID);
		
		assertThat(municipalities.get(0).municipality_name, is("myMuni"));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void returnsMunicipalityFromMunicipalityId(){
		final Municipality MUNICIPALITY = new Municipality();
		MUNICIPALITY.municipality_name = "myMuni";
		final long MUNICIPALITYID = 123458L;
		when(jdbcTemplate.queryForObject(Matchers.anyString(), Matchers.anyMap(), Matchers.<RowMapper<Municipality>>any()))
        .thenReturn(MUNICIPALITY);

		Municipality municipality = municipalityDAO.getByMunicipalityId(MUNICIPALITYID);
		
		assertThat(municipality.municipality_name, is("myMuni"));
	}
	
	@Test
	public void returnsAllMunicipalities(){
		final Municipality MUNICIPALITY = new Municipality();
		MUNICIPALITY.municipality_name = "myMunicipality";
		final List<Municipality> MUNICIPALITIES = Arrays.asList(new Municipality[]{MUNICIPALITY});
		
		when(jdbcTemplate.query(Matchers.anyString(), Matchers.<RowMapper<Municipality>>any()))
        .thenReturn(MUNICIPALITIES);

		List<Municipality> municipalities = municipalityDAO.getAllMunicipalities();
		
		assertThat(municipalities.get(0).municipality_name, is("myMunicipality"));
	}
	
	
}
