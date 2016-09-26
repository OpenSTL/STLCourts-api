package svc.data.citations;

import static org.junit.Assert.*;

import static org.hamcrest.CoreMatchers.*;

import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.*;
import org.springframework.test.context.web.WebAppConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;

import svc.Application;
import svc.models.Citation;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class CitationDAOIntegrationTest {

	@Test
	public void GetCitationByIDSuccessful() {
		CitationDAO dao = new CitationDAO();

		Citation citation = dao.getByCitationId(1L);

		assertThat(citation, is(notNullValue()));
		assertThat(citation.citation_number, is("789674515"));
	}

}
