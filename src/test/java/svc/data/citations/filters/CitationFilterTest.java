package svc.data.citations.filters;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Lists;

import svc.managers.CourtManager;
import svc.models.Citation;
import svc.models.Court;
import svc.models.Violation;
import svc.types.HashableEntity;

@RunWith(MockitoJUnitRunner.class)
public class CitationFilterTest {
	@InjectMocks
	CitationFilter citationFilter;
	
	@Mock
	CourtManager courtManagerMock;
	
	@Test
	public void correctlyKeepsCitations(){
		Court COURT = new Court();
		COURT.citation_expires_after_days = -1;
		
		Citation CITATION = new Citation();
		CITATION.id = 3;
		CITATION.court_id = new HashableEntity<Court>(Court.class,4L);
		
		List<Violation> VIOLATIONS = Lists.newArrayList();
		CITATION.violations = VIOLATIONS;
		
		String courtDateString = "09/10/2016 14:33";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
		
		CITATION.court_dateTime = LocalDateTime.parse(courtDateString, formatter);
		
		List<Citation> CITATIONS = Lists.newArrayList(CITATION);
		
		when(courtManagerMock.getCourtById(CITATION.court_id.getValue())).thenReturn(COURT);
		assertThat(citationFilter.RemoveCitationsWithExpiredDates(CITATIONS).size(), is(1));
		
		COURT.citation_expires_after_days = 4;
		CITATION.court_dateTime = LocalDateTime.now().plusDays(1);
		
		CITATIONS = Lists.newArrayList(CITATION);
		assertThat(citationFilter.RemoveCitationsWithExpiredDates(CITATIONS).size(), is(1));
	}
	
	@Test
	public void correctlyFiltersCitations(){
		Court COURT = new Court();
		COURT.citation_expires_after_days = 1;
		
		Citation CITATION = new Citation();
		CITATION.id = 3;
		CITATION.court_id = new HashableEntity<Court>(Court.class,4L);
		
		List<Violation> VIOLATIONS = Lists.newArrayList();
		CITATION.violations = VIOLATIONS;
		
		String courtDateString = "09/10/2016 14:33";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
		
		CITATION.court_dateTime = LocalDateTime.parse(courtDateString, formatter);
		
		List<Citation> CITATIONS = Lists.newArrayList(CITATION);
		when(courtManagerMock.getCourtById(CITATION.court_id.getValue())).thenReturn(COURT);
		
		assertThat(citationFilter.RemoveCitationsWithExpiredDates(CITATIONS).size(), is(0));
	}
	
	@Test
	public void correctlyKeepsOldWarrantCitations(){
		Court COURT = new Court();
		COURT.citation_expires_after_days = 3;
		
		Citation CITATION = new Citation();
		CITATION.id = 3;
		CITATION.court_id = new HashableEntity<Court>(Court.class,4L);
		
		Violation VIOLATION = new Violation();
		VIOLATION.warrant_status = true;
		
		List<Violation> VIOLATIONS = Lists.newArrayList(VIOLATION);
		CITATION.violations = VIOLATIONS;
		
		String courtDateString = "09/10/2016 14:33";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
		
		CITATION.court_dateTime = LocalDateTime.parse(courtDateString, formatter);
		
		List<Citation> CITATIONS = Lists.newArrayList(CITATION);
		
		when(courtManagerMock.getCourtById(CITATION.court_id.getValue())).thenReturn(COURT);
		assertThat(citationFilter.RemoveCitationsWithExpiredDates(CITATIONS).size(), is(1));   
	}
}
