package svc.data.municipal;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import svc.Application;
import svc.models.Court;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class CourtDAOIntegrationTest {
	@Autowired
    private CourtDAO dao;
	
	@Test
	public void GetByCourtIdSuccessful(){
		Court court = dao.getCourtById(1L);

		assertThat(court, is(notNullValue()));
	}

}
