package svc.data.citations;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;

import static org.hamcrest.CoreMatchers.*;

import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import svc.models.Citation;

@RunWith(MockitoJUnitRunner.class)
public class CitationDAOTest {
    @InjectMocks
    CitationDAO citationDAO;
    
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
        DateFormat  format = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        Date date = format.parse(dateString);
		
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
        
        final List<Citation> CITATIONS = Arrays.asList(new Citation[]{CITATION});

        when(jdbcTemplate.query(Matchers.anyString(),Matchers.anyMap(), Matchers.<RowMapper<Citation>>any()))
        .thenReturn(CITATIONS);
        
        String dateString = "08/05/1965";
        DateFormat  format = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        Date date = format.parse(dateString);

        List<Citation> citations = citationDAO.getByDOBAndLicense(date, "someLiscensNumber");
        
        assertThat(citations.get(0).id, is(3));
    }
	
	@SuppressWarnings("unchecked")
	@Test
	public void returnsCitationsWhenGivenDOBLastNameAndMultipleMunicipalities() throws ParseException {
        final Citation CITATION = new Citation();
        CITATION.id = 3;
        
        final List<Citation> CITATIONS = Arrays.asList(new Citation[]{CITATION});

        when(jdbcTemplate.query(Matchers.anyString(),Matchers.anyMap(), Matchers.<RowMapper<Citation>>any()))
        .thenReturn(CITATIONS);
        
        String dateString = "08/05/1965";
        DateFormat  format = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        Date date = format.parse(dateString);

        List<Citation> citations = citationDAO.getByDOBAndNameAndMunicipalities(date, "someLastName", Arrays.asList(new String[]{"muni1","muni2"}));
        
        assertThat(citations.get(0).id, is(3));


	}

}
