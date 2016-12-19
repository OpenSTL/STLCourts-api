package svc.data.municipal;

import static org.junit.Assert.*;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.*;
import org.junit.Test;
import org.junit.runner.RunWith;

import svc.Application;
import svc.models.Municipality;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class MunicipalityDAOIntegrationTest {
	
	@Autowired
    private MunicipalityDAO dao;

	@Test
	public void GetMunicipalitiesByCourtIdSuccessful() {
		List<Municipality> municipalities = dao.getByCourtId(1L);

		assertThat(municipalities, is(notNullValue()));
		assertThat(municipalities.get(0).municipality_name, is("Ballwin"));
	}
	
	@Test
	public void GetMunicipalitiesByCourtIdUnSuccessful() {
		List<Municipality> municipalities = dao.getByCourtId(0L);

		assertTrue(municipalities.isEmpty());
	}
	
	@Test
	public void GetMunicipalityByMunicipalityIdSuccessful() {
		Municipality municipality = dao.getByMunicipalityId(1L);

		assertThat(municipality, is(notNullValue()));
		assertThat(municipality.municipality_name, is("Ballwin"));
	}
	
	@Test
	public void GetMunicipalityByMunicipalityIdUnSuccessful() {
		Municipality municipality = dao.getByMunicipalityId(0L);

		assertThat(municipality, is(nullValue()));
	}
	
	@Test
	public void GetAllMunicipalitiesSuccessful() {
		List<Municipality> municipalities = dao.getAllMunicipalities();

		assertThat(municipalities, is(notNullValue()));
		assertThat(municipalities.isEmpty(),is(false));
	}
}
