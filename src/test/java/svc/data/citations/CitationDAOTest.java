package svc.data.citations;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import svc.models.Citation;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.core.io.ResourceLoader.CLASSPATH_URL_PREFIX;

@RunWith(MockitoJUnitRunner.class)
public class CitationDAOTest {
    @InjectMocks
    CitationDAO citationDAO;

    @Mock
    private ResourceLoader resourceLoader;
    
    @Mock
    NamedParameterJdbcTemplate jdbcTemplate;

    @SuppressWarnings("unchecked")
	@Test
    public void returnsCitationGivenCitationId(){
    	final Citation CITATION = new Citation();
        CITATION.citation_number = "myCitationNumber";
		final long CITATIONID = 123458L;
		
		when(jdbcTemplate.queryForObject(Matchers.anyString(), Matchers.anyMap(), Matchers.<RowMapper<Citation>>any()))
        .thenReturn(CITATION);

		Citation citation = citationDAO.getByCitationId(CITATIONID);
		
		assertThat(citation.citation_number, is("myCitationNumber"));
    }
    
    @SuppressWarnings("unchecked")
	@Test
    public void returnsCitationGivenCitationNumber(){
    	final Citation CITATION = new Citation();
        CITATION.id = 3;
		final String CITATIONNUMBER = "F3453";
		
		when(jdbcTemplate.queryForObject(Matchers.anyString(), Matchers.anyMap(), Matchers.<RowMapper<Citation>>any()))
        .thenReturn(CITATION);

		Citation citation = citationDAO.getByCitationNumber(CITATIONNUMBER);
		
		assertThat(citation.id, is(3));
    }
    
    @SuppressWarnings("unchecked")
	@Test
    public void returnsCitationGivenCitationNumberAndDOB() throws ParseException{
    	final Citation CITATION = new Citation();
        CITATION.id = 3;
		final String CITATIONNUMBER = "F3453";
		String dateString = "08/05/1965";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate date = LocalDate.parse(dateString,formatter);
		
		when(jdbcTemplate.queryForObject(Matchers.anyString(), Matchers.anyMap(), Matchers.<RowMapper<Citation>>any()))
        .thenReturn(CITATION);

		Citation citation = citationDAO.getByCitationNumberAndDOB(CITATIONNUMBER, date);
		
		assertThat(citation.id, is(3));
    }
    
    @SuppressWarnings("unchecked")
	@Test
    public void returnsCitationsGivengeDOBAndLicense() throws ParseException{
    	final Citation CITATION = new Citation();
        CITATION.id = 3;
        
        final List<Citation> CITATIONS = Lists.newArrayList(CITATION);

        when(jdbcTemplate.query(Matchers.anyString(),Matchers.anyMap(), Matchers.<RowMapper<Citation>>any()))
        .thenReturn(CITATIONS);
        
        String dateString = "08/05/1965";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate date = LocalDate.parse(dateString,formatter);

        List<Citation> citations = citationDAO.getByDOBAndLicense(date, "someLiscensNumber");
        
        assertThat(citations.get(0).id, is(3));
    }
	
	@SuppressWarnings("unchecked")
	@Test
	public void returnsCitationsWhenGivenDOBLastNameAndMultipleMunicipalities() throws ParseException, IOException {
        final Citation CITATION = new Citation();
        CITATION.id = 3;
        
        final List<Citation> CITATIONS = Lists.newArrayList(CITATION);

        Resource resource = mock(Resource.class);
        when(resource.getInputStream()).thenReturn(null);
        when(resourceLoader.getResource(CLASSPATH_URL_PREFIX + "sql/citation/get-by-location.sql")).thenReturn(resource);

        when(jdbcTemplate.query(Matchers.anyString(),Matchers.anyMap(), Matchers.<RowMapper<Citation>>any())).thenReturn(CITATIONS);
        
        String dateString = "08/05/1965";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate date = LocalDate.parse(dateString,formatter);

        List<Citation> citations = citationDAO.getByDOBAndNameAndMunicipalities(date, "someLastName", Lists.newArrayList(19L, 20L));
        
        assertThat(citations.get(0).id, is(3));
	}
}
