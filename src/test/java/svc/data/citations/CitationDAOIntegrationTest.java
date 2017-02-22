package svc.data.citations;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static org.hamcrest.CoreMatchers.*;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.*;
import org.springframework.test.context.web.WebAppConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;

import svc.Application;
import svc.models.Citation;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class CitationDAOIntegrationTest {
	
	@Autowired
    private CitationDAO dao;

	@Test
	public void GetCitationByIDSuccessful() {
		Citation citation = dao.getByCitationId(837L);

		assertThat(citation, is(notNullValue()));
		assertThat(citation.citation_number, is("865365678"));
	}
	
	@Test
	public void GetCitationByNumberSuccessful(){
		Citation citation = dao.getByCitationNumber("209092850");

		assertThat(citation, is(notNullValue()));
		assertThat(citation.id, is(911));
	}

	@Test
	public void GetCitationByCitationNumberAndDOBSuccessful() throws ParseException{
		String dateString = "04/10/1992";
        DateFormat  format = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        Date date = format.parse(dateString);
        
        Citation citation = dao.getByCitationNumberAndDOB("13938567", date);
        assertThat(citation, is(notNullValue()));
		assertThat(citation.id, is(7));
	}
	
	@Test
	public void GetCitationsByDOBAndLicenseSuccessful() throws ParseException{
		String dateString = "05/18/1987";
        DateFormat  format = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        Date date = format.parse(dateString);
        
        List<Citation> citations = dao.getByDOBAndLicense(date, "S878479512");
        assertThat(citations, is(notNullValue()));
		assertThat(citations.size(), is(3));
		assertThat(citations.get(0).first_name, is("Brenda"));
	}
	
	@Test
	public void GetCitationsByDOBAndLastNameAndMunicipalitiesSuccessful() throws ParseException{
		String dateString = "05/18/1987";
        DateFormat  format = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        Date date = format.parse(dateString);
        List<Long> municipalities = Lists.newArrayList(33L, 44L);
        
        List<Citation> citations = dao.getByDOBAndNameAndMunicipalities(date, "Peterson", municipalities);
        assertThat(citations, is(notNullValue()));
		assertThat(citations.size(), is(2));
		assertThat(citations.get(0).first_name, is("Brenda"));
	}
}
