package svc.data.municipal;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import svc.Application;
import svc.models.Court;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class CourtDAOIntegrationTest {
	@Autowired
    private CourtDAO dao;
	
	@Test
	public void GetByCourtIdSuccessful(){
		Court court = dao.getByCourtId(1L);

		assertThat(court, is(notNullValue()));
		assertThat(court.municipality, is("Ballwin"));
	}

}
