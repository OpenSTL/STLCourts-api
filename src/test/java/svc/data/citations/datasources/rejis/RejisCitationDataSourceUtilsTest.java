package svc.data.citations.datasources.rejis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


import com.google.common.collect.Lists;

import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import svc.data.citations.datasources.rejis.models.RejisCaseList;
import svc.data.citations.datasources.rejis.models.RejisFullCitation;
import svc.data.citations.datasources.rejis.models.RejisPartialCitation;
import svc.data.citations.datasources.rejis.transformers.RejisCitationTransformer;
import svc.models.Citation;

@RunWith(MockitoJUnitRunner.class)
public class RejisCitationDataSourceUtilsTest {
	
	
	@InjectMocks
	RejisCitationDataSourceUtils mockRejisCitationDataSourceUtils;
	
	@Mock
    UriComponentsBuilder mockUriComponentsBuilder;
	
	
	@Mock
	RejisConfiguration mockRejisConfiguration;
	
	@Mock
    RestTemplate restTemplate;
	@Mock
	RejisCitationTransformer mockCitationTransformer;
	
	ResponseEntity<RejisCaseList> caseListResponse;
	ResponseEntity<RejisFullCitation> fullCaseResponse;
	RejisCaseList rejisCaseList;
	RejisPartialCitation rejisPartialCitation;
	RejisFullCitation rejisFullCitation;
	
	
	@Before
	public void init(){
		
		rejisCaseList = new RejisCaseList();
		rejisCaseList.totalPages = 1;
		rejisCaseList.pageNumber = 1;
		rejisPartialCitation = new RejisPartialCitation();
		rejisPartialCitation.caseNumber = "123";
		rejisCaseList.caseIndexRows = Lists.newArrayList(rejisPartialCitation);
		
		caseListResponse = new ResponseEntity<RejisCaseList>(rejisCaseList, HttpStatus.ACCEPTED);
		
		rejisFullCitation = new RejisFullCitation();
		rejisFullCitation.ticketNumber = "123";
		rejisFullCitation.defendantName = "Some One";
		rejisFullCitation.lastName = "ONE";
		rejisFullCitation.dob = "1996-01-02T00:00:00";
		rejisFullCitation.violationDateTime = "2015-05-05T08:08:08";
		rejisFullCitation.nextCourtDate = "2017-08-01T12:30:00";
		rejisFullCitation.originalCourtDate = "2017-08-01T12:30:00";
		rejisFullCitation.agencyId = "B";
		rejisFullCitation.defendantAddress = "123 AnyStreet  Anytown, MO  12345";
		
		fullCaseResponse = new ResponseEntity<RejisFullCitation>(rejisFullCitation, HttpStatus.ACCEPTED); 
	}
    
	
	@Test
	public void returnsUriComponentsBuilderFromCitationNumber(){
		final int pageNumber = 1;
		final String citationNumber = "ABC";
		final LocalDate dob = LocalDate.parse("1996-01-02");
		final List<String> MUNICIPALITYCODES = Lists.newArrayList("A","B","C");
		
		when(mockRejisConfiguration.getRootUrl()).thenReturn("http://www.myUrl.com");
		UriComponentsBuilder uriComponentsBuilder = mockRejisCitationDataSourceUtils.getCitationNumberBuilder(pageNumber, citationNumber, dob, MUNICIPALITYCODES);
		assertTrue(uriComponentsBuilder.toUriString().contains("http://www.myUrl.com/"+RejisConstants.BY_TICKET));
		assertTrue(uriComponentsBuilder.toUriString().contains(RejisConstants.TICKET_NUMBER+'='+citationNumber));
		assertTrue(uriComponentsBuilder.toUriString().contains(RejisConstants.DOB+'='+dob));
		assertTrue(uriComponentsBuilder.toUriString().contains(RejisConstants.AGENCY_ID+'='+"A,B,C"));
		assertTrue(uriComponentsBuilder.toUriString().contains(RejisConstants.PAGE_NUMBER+'='+pageNumber));
		assertTrue(uriComponentsBuilder.toUriString().contains(RejisConstants.ROWS_PER_PAGE+"=50"));
		assertTrue(uriComponentsBuilder.toUriString().contains(RejisConstants.RESULT_FORMAT+"=json"));
	}
	
	@Test
	public void returnsUriComponentsBuilderFromLicense(){
		final int pageNumber = 1;
		final String dlNum = "ABC";
		final String dlState = "MO";
		final String lastName = "SMITH";
		final LocalDate dob = LocalDate.parse("1996-01-02");
		final List<String> MUNICIPALITYCODES = Lists.newArrayList("A","B","C");
		
		when(mockRejisConfiguration.getRootUrl()).thenReturn("http://www.myUrl.com");
		UriComponentsBuilder uriComponentsBuilder = mockRejisCitationDataSourceUtils.getLicenseBuilder(pageNumber, dlNum, dlState, lastName, dob, MUNICIPALITYCODES);
		assertTrue(uriComponentsBuilder.toUriString().contains("http://www.myUrl.com/"+RejisConstants.BY_VEHICLE_LICENSE));
		assertTrue(uriComponentsBuilder.toUriString().contains(RejisConstants.VEHICLE_LIC_NUMBER+'='+dlNum));
		assertTrue(uriComponentsBuilder.toUriString().contains(RejisConstants.VEHICLE_LIC_STATE+'='+dlState));
		assertTrue(uriComponentsBuilder.toUriString().contains(RejisConstants.DOB+'='+dob));
		assertTrue(uriComponentsBuilder.toUriString().contains(RejisConstants.LAST_NAME+'='+lastName));
		assertTrue(uriComponentsBuilder.toUriString().contains(RejisConstants.AGENCY_ID+'='+"A,B,C"));
		assertTrue(uriComponentsBuilder.toUriString().contains(RejisConstants.PAGE_NUMBER+'='+pageNumber));
		assertTrue(uriComponentsBuilder.toUriString().contains(RejisConstants.ROWS_PER_PAGE+"=50"));
		assertTrue(uriComponentsBuilder.toUriString().contains(RejisConstants.RESULT_FORMAT+"=json"));
	}
	
	@Test
	public void returnsUriComponentsBuilderFromName(){
		final int pageNumber = 1;
		final String lastName = "SMITH";
		final LocalDate dob = LocalDate.parse("1996-01-02");
		final List<String> MUNICIPALITYCODES = Lists.newArrayList("A","B","C");
		
		when(mockRejisConfiguration.getRootUrl()).thenReturn("http://www.myUrl.com");
		UriComponentsBuilder uriComponentsBuilder = mockRejisCitationDataSourceUtils.getNameBuilder(pageNumber, lastName, dob, MUNICIPALITYCODES);
		assertTrue(uriComponentsBuilder.toUriString().contains("http://www.myUrl.com/"+RejisConstants.BY_NAME));
		assertTrue(uriComponentsBuilder.toUriString().contains(RejisConstants.DOB+'='+dob));
		assertTrue(uriComponentsBuilder.toUriString().contains(RejisConstants.LAST_NAME+'='+lastName));
		assertTrue(uriComponentsBuilder.toUriString().contains(RejisConstants.AGENCY_ID+'='+"A,B,C"));
		assertTrue(uriComponentsBuilder.toUriString().contains(RejisConstants.PAGE_NUMBER+'='+pageNumber));
		assertTrue(uriComponentsBuilder.toUriString().contains(RejisConstants.ROWS_PER_PAGE+"=50"));
		assertTrue(uriComponentsBuilder.toUriString().contains(RejisConstants.RESULT_FORMAT+"=json"));
	}
    
	@Test
	public void returnsRejisCaseList(){
		when(restTemplate.exchange(any(URI.class), eq(HttpMethod.GET), any(HttpEntity.class), eq(RejisCaseList.class)))
    	.thenReturn(caseListResponse);
		
		RejisCaseList rejisCaseListReturned = mockRejisCitationDataSourceUtils.performRestTemplateCall(URI.create("http://www.google.com"));
		assertEquals(rejisCaseList, rejisCaseListReturned);
		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void returnsEmptyCitationsOnRejisCaseListRestTemplateError(){
		when(restTemplate.exchange(any(URI.class), eq(HttpMethod.GET), any(HttpEntity.class), eq(RejisCaseList.class)))
		.thenThrow(RestClientException.class);
		
		RejisCaseList rejisCaseListReturned = mockRejisCitationDataSourceUtils.performRestTemplateCall(URI.create("http://www.google.com"));
		assertNull(rejisCaseListReturned);
		
	}
	
	@Test
	public void returnsCitationsFromGetFullCitations(){
		
		final Citation CITATION = new Citation();
		CITATION.citation_number = "ABCDEF";
		
		when(mockRejisConfiguration.getRootUrl()).thenReturn("http://www.myUrl.com");
		when(restTemplate.exchange(any(URI.class), eq(HttpMethod.GET), any(HttpEntity.class), eq(RejisFullCitation.class)))
		 .thenReturn(fullCaseResponse);
		when(mockCitationTransformer.fromRejisFullCitation(rejisFullCitation, rejisPartialCitation))
		 .thenReturn(CITATION);
		
		List<Citation> citationsReturned = mockRejisCitationDataSourceUtils.getFullCitations(Arrays.asList(rejisPartialCitation));
		
		assertEquals(citationsReturned.get(0), CITATION);
		
	}
    
}
