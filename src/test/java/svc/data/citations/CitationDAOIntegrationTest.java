package svc.data.citations;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
	public void GetCitationByCitationNumberAndDOBSuccessful() throws ParseException{
		String dateString = "04/10/1992";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate date = LocalDate.parse(dateString,formatter);
        
        List<Citation> citations = dao.getByCitationNumberAndDOB("13938567", date);
        assertThat(citations.get(0), is(notNullValue()));
		assertThat(citations.get(0).id, is(7));
	}
	
	@Test
	public void GetCitationsByDOBAndLicenseSuccessful() throws ParseException{
		String dateString = "05/18/1987";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate date = LocalDate.parse(dateString,formatter);
        
        List<Citation> citations = dao.getByLicenseAndDOB("S878479512", date);
        assertThat(citations, is(notNullValue()));
		assertThat(citations.size(), is(3));
		assertThat(citations.get(0).first_name, is("Brenda"));
	}
	
	@Test
	public void GetCitationsByDOBAndLastNameAndMunicipalitiesSuccessful() throws ParseException{
		String dateString = "05/18/1987";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate date = LocalDate.parse(dateString,formatter);

        List<Long> municipalities = Lists.newArrayList(33L, 44L);
        
        List<Citation> citations = dao.getByNameAndMunicipalitiesAndDOB("Peterson", municipalities, date);
        assertThat(citations, is(notNullValue()));
		assertThat(citations.size(), is(2));
		assertThat(citations.get(0).first_name, is("Brenda"));
	}

	@Test
	public void CitationWithNullDateValuesIsCorrectlyHandled() throws ParseException{
		String dateString = "02/24/1987";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate date = LocalDate.parse(dateString,formatter);

		List<Citation> citations = dao.getByDOBAndLicense(date, "N806453191");
		assertThat(citations.get(0).citation_date, is(nullValue()));

		dateString = "11/21/1994";
		date = LocalDate.parse(dateString,formatter);
		citations = dao.getByDOBAndLicense(date, "E501444452");
		assertThat(citations.get(0).court_dateTime, is(nullValue()));
	}
}
