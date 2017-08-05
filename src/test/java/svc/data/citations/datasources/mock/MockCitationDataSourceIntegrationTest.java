package svc.data.citations.datasources.mock;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import svc.Application;
import svc.models.Citation;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class MockCitationDataSourceIntegrationTest {
    @Autowired
    private MockCitationDataSource mockCitationDataSource;

    @Test
    public void GetCitationByCitationNumberAndDOBSuccessful() throws ParseException {
        String dateString = "04/10/1992";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate date = LocalDate.parse(dateString,formatter);

        List<Citation> citations = mockCitationDataSource.getByCitationNumberAndDOB("13938567", date);
        assertThat(citations.get(0), is(notNullValue()));
        assertThat(citations.get(0).id, is(7));
    }

    @Test
    public void GetCitationsByDOBAndLicenseSuccessful() throws ParseException{
        String dateString = "05/18/1987";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate date = LocalDate.parse(dateString,formatter);
        

        List<Citation> citations = mockCitationDataSource.getByLicenseAndDOB("S878479512","MO", date, "Peterson");
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

        List<Citation> citations = mockCitationDataSource.getByNameAndMunicipalitiesAndDOB("Peterson", municipalities, date);
        assertThat(citations, is(notNullValue()));
        assertThat(citations.size(), is(2));
        assertThat(citations.get(0).first_name, is("Brenda"));
    }

    @Test
    public void CitationWithNullDateValuesIsCorrectlyHandled() throws ParseException{
        String dateString = "02/24/1987";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate date = LocalDate.parse(dateString,formatter);

        List<Citation> citations = mockCitationDataSource.getByLicenseAndDOB("N806453191", "MO",date, "Burke");
        assertThat(citations.get(0).citation_date, is(nullValue()));

        dateString = "11/21/1994";
        date = LocalDate.parse(dateString,formatter);
        citations = mockCitationDataSource.getByLicenseAndDOB("E501444452","MO", date, "Ramirez");
        assertThat(citations.get(0).court_dateTime, is(nullValue()));
    }
}
