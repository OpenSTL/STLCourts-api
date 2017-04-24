package svc.data.citations.filters;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Lists;

import svc.data.citations.datasources.CITATION_DATASOURCE;
import svc.models.Citation;

@RunWith(MockitoJUnitRunner.class)
public class CitationDateFilterTest {
	
	@Test
	public void correctlyKeepsCitations(){
		Citation CITATION = new Citation();
        CITATION.citation_datasource = CITATION_DATASOURCE.MOCK;
        CITATION.id = 3;
        String courtDateString = "09/10/2016 14:33";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
        
        CITATION.court_dateTime = LocalDateTime.parse(courtDateString, formatter);
        
        List<Citation> CITATIONS = Lists.newArrayList(CITATION);
        
        assertThat(CitationDateFilter.FilterDates(CITATIONS).size(), is(1));
        
        CITATION.court_dateTime = LocalDateTime.now().plusDays(1);
        CITATION.citation_datasource = CITATION_DATASOURCE.TYLER;
        CITATIONS = Lists.newArrayList(CITATION);
        assertThat(CitationDateFilter.FilterDates(CITATIONS).size(), is(1));
        
	}
	
	@Test
	public void correctlyFiltersCitations(){
		Citation CITATION = new Citation();
        CITATION.citation_datasource = CITATION_DATASOURCE.TYLER;
        CITATION.id = 3;
        String courtDateString = "09/10/2016 14:33";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
        
        CITATION.court_dateTime = LocalDateTime.parse(courtDateString, formatter);
        
        List<Citation> CITATIONS = Lists.newArrayList(CITATION);
        
        assertThat(CitationDateFilter.FilterDates(CITATIONS).size(), is(0));
	}

}
