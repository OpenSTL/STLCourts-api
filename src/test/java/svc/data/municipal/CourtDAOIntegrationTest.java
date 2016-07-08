package svc.data.municipal;

import static org.junit.Assert.*;

import static org.hamcrest.CoreMatchers.*;

import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.*;
import org.springframework.test.context.web.WebAppConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;

import svc.Application;
import svc.models.Court;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class CourtDAOIntegrationTest {

	@Test
	public void GetByCourtIdSuccessful() {
		CourtDAO dao = new CourtDAO();

		Court court = dao.getByCourtId(1);

		assertThat(court, is(notNullValue()));
		assertThat(court.municipality, is("Ballwin"));
	}

}
