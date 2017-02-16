package svc.data.municipal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import svc.Application;
import svc.models.Municipality;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class MunicipalityDAOIntegrationTest {
	
	@Autowired
    private MunicipalityDAO dao;

	@Test
	public void GetMunicipalitiesByCourtIdSuccessful() {
		List<Municipality> municipalities = dao.getByCourtId(1L);

		assertThat(municipalities, is(notNullValue()));
		assertThat(municipalities.get(0).name, is("Ballwin"));
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
		assertThat(municipality.name, is("Ballwin"));
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
