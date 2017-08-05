package svc.data.citations;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import svc.data.citations.datasources.mock.MockCitationDataSource;
import svc.models.Citation;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class CitationDAOTest {
    @InjectMocks
    CitationDAO citationDAO;
    
    @Mock
    CitationDataSourceFactory citationDataSourceFactory;

    @Mock
    MockCitationDataSource mockCitationDataSource;
    
	@Test
    public void returnsCitationGivenCitationNumberAndDOB() throws ParseException{
    	final Citation CITATION = new Citation();
        CITATION.id = 3;
		final String CITATIONNUMBER = "F3453";
		String dateString = "08/05/1965";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate date = LocalDate.parse(dateString,formatter);
        final List<Citation> CITATIONS = Lists.newArrayList(CITATION);

        when(mockCitationDataSource.getByCitationNumberAndDOB(CITATIONNUMBER, date)).thenReturn(CITATIONS);
        final List<CitationDataSource> SOURCES = Lists.newArrayList(mockCitationDataSource);

        when(citationDataSourceFactory.getAllCitationDataSources()).thenReturn(SOURCES);

		List<Citation> citations = citationDAO.getByCitationNumberAndDOB(CITATIONNUMBER, date);

		assertEquals(citations.size(), 1);
		assertThat(citations.get(0).id, is(3));
    }
    
	@Test
    public void returnsCitationsGivenLicenseAndDOB() throws ParseException{
    	final Citation CITATION = new Citation();
        CITATION.id = 3;
        String licenseNunmber = "someLiscensNumber";
        String licenseState = "MO";
        String dateString = "08/05/1965";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate date = LocalDate.parse(dateString,formatter);
        String lastName = "someName";

        final List<Citation> CITATIONS = Lists.newArrayList(CITATION);

        when(mockCitationDataSource.getByLicenseAndDOB(licenseNunmber, licenseState, date, lastName)).thenReturn(CITATIONS);
        final List<CitationDataSource> SOURCES = Lists.newArrayList(mockCitationDataSource);

        when(citationDataSourceFactory.getAllCitationDataSources()).thenReturn(SOURCES);

        List<Citation> citations = citationDAO.getByLicenseAndDOB(licenseNunmber,licenseState, date, lastName);

        assertThat(citations.get(0).id, is(3));
    }

	@Test
	public void returnsCitationsWhenGivenDOBLastNameAndMultipleMunicipalities() throws ParseException, IOException {
        final Citation CITATION = new Citation();
        CITATION.id = 3;
        String dateString = "08/05/1965";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate date = LocalDate.parse(dateString,formatter);
        List<Long> munis = Lists.newArrayList(19L, 20L);
        String name = "someLastName";

        final List<Citation> CITATIONS = Lists.newArrayList(CITATION);

        when(mockCitationDataSource.getByNameAndMunicipalitiesAndDOB(name, munis, date)).thenReturn(CITATIONS);
        final List<CitationDataSource> SOURCES = Lists.newArrayList(mockCitationDataSource);

        when(citationDataSourceFactory.getCitationDataSourcesForMunicipalities(munis)).thenReturn(SOURCES);

        List<Citation> citations = citationDAO.getByNameAndMunicipalitiesAndDOB(name, munis, date);

        assertThat(citations.get(0).id, is(3));
	}
}
