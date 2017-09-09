package svc.data.citations.datasources.rejis;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
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

import svc.data.citations.datasources.CITATION_DATASOURCE;
import svc.data.citations.datasources.rejis.models.RejisCaseList;
import svc.data.citations.datasources.rejis.models.RejisFullCitation;
import svc.data.citations.datasources.rejis.models.RejisPartialCitation;
import svc.data.citations.datasources.rejis.transformers.RejisCitationTransformer;
import svc.data.citations.datasources.transformers.CourtIdTransformer;
import svc.data.citations.filters.CitationFilter;
import svc.models.Citation;
import svc.models.Court;
import svc.types.HashableEntity;

@RunWith(MockitoJUnitRunner.class)
public class RejisCitationDataSourceTest {
	@InjectMocks
	RejisCitationDataSource mockRejisCitationDataSource;
	
	@Mock
	RejisConfiguration mockRejisConfiguration;
	@Mock
	RejisMunicipalityCodesFactory mockRejisMunicipalityCodesFactory;
	@Mock
    UriComponentsBuilder mockUriComponentsBuilder;
	@Mock
    RestTemplate restTemplate;
	@Mock
	RejisCitationTransformer mockCitationTransformer;
	@Mock
	CitationFilter mockCitationFilter;
	@Mock
	CourtIdTransformer mockCourtIdTransformer;
	
	ResponseEntity<RejisCaseList> caseListResponse;
	ResponseEntity<RejisFullCitation> fullCaseResponse;
	
	
	@Before
	public void init(){
		when(mockCourtIdTransformer.lookupCourtId(CITATION_DATASOURCE.REJIS, "B")).thenReturn(new HashableEntity<Court>(Court.class, 6L));
		
		final RejisCaseList rejisCaseList = new RejisCaseList();
		rejisCaseList.totalPages = 1;
		rejisCaseList.pageNumber = 1;
		final RejisPartialCitation rejisPartialCitation = new RejisPartialCitation();
		rejisPartialCitation.caseNumber = "123";
		rejisCaseList.caseIndexRows = Lists.newArrayList(rejisPartialCitation);
		
		caseListResponse = new ResponseEntity<RejisCaseList>(rejisCaseList, HttpStatus.ACCEPTED);
		
		final RejisFullCitation rejisFullCitation = new RejisFullCitation();
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
    
	@SuppressWarnings("unchecked")
	@Test
	public void returnsCitationsGivenCitationNumberAndDOB(){
		final List<String> MUNICIPALITYCODES = Lists.newArrayList("A","B","C");
		final Citation CITATION = new Citation();
		CITATION.citation_number = "1234";
		
		when(mockRejisMunicipalityCodesFactory.getAllMunicipalityCodes()).thenReturn(MUNICIPALITYCODES);
		when(restTemplate.exchange(any(URI.class), eq(HttpMethod.GET), any(HttpEntity.class), eq(RejisCaseList.class)))
        .thenReturn(caseListResponse);
		when(restTemplate.exchange(any(URI.class), eq(HttpMethod.GET), any(HttpEntity.class), eq(RejisFullCitation.class)))
        .thenReturn(fullCaseResponse);
		when(mockRejisConfiguration.getRootUrl()).thenReturn("http://www.myUrl.com");
		when(mockCitationTransformer.fromRejisFullCitation(any(RejisFullCitation.class), any(RejisPartialCitation.class)))
				.thenReturn(CITATION);
		when(mockCitationFilter.Filter(any(List.class), any(LocalDate.class), anyString()))
		.thenReturn(Arrays.asList(CITATION));
		
		List<Citation> returnedCitation = mockRejisCitationDataSource.getByCitationNumberAndDOB("123", LocalDate.parse("1996-01-02"));
		
		assertThat(returnedCitation.get(0).citation_number, is("1234"));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void returnsCitationsGivenLicenseAndDOB(){
		final String DRIVERSLICENSENUMBER = "ABCDE";
		final String DRIVERSLICENSESTATE = "AZ";
		final LocalDate DOB = LocalDate.parse("1999-06-01");
		final String LASTNAME = "someName";
		
		final List<String> MUNICIPALITYCODES = Lists.newArrayList("A","B","C");
		final Citation CITATION = new Citation();
		CITATION.citation_number = "1234";
		
		when(mockRejisMunicipalityCodesFactory.getAllMunicipalityCodes()).thenReturn(MUNICIPALITYCODES);
		when(restTemplate.exchange(any(URI.class), eq(HttpMethod.GET), any(HttpEntity.class), eq(RejisCaseList.class)))
        .thenReturn(caseListResponse);
		when(restTemplate.exchange(any(URI.class), eq(HttpMethod.GET), any(HttpEntity.class), eq(RejisFullCitation.class)))
        .thenReturn(fullCaseResponse);
		when(mockRejisConfiguration.getRootUrl()).thenReturn("http://www.myUrl.com");
		when(mockCitationTransformer.fromRejisFullCitation(any(RejisFullCitation.class), any(RejisPartialCitation.class)))
				.thenReturn(CITATION);
		when(mockCitationFilter.Filter(any(List.class), any(LocalDate.class), anyString()))
		.thenReturn(Arrays.asList(CITATION));
		
		List<Citation> returnedCitation = mockRejisCitationDataSource.getByLicenseAndDOBAndLastName(DRIVERSLICENSENUMBER,DRIVERSLICENSESTATE, DOB,LASTNAME);
		
		assertThat(returnedCitation.get(0).citation_number, is("1234"));
	}
	
	
	@SuppressWarnings("unchecked")
	@Test
	public void returnsCitationsGivenNameAndMunicipalitiesAndDOB(){
		final String NAME = "Smith";
		final List<Long> MUNICIPALITIES = Lists.newArrayList(5L);
		final LocalDate DOB = LocalDate.parse("1999-06-01");
		
		final Citation CITATION = new Citation();
		CITATION.citation_number = "1234";
		
		when(restTemplate.exchange(any(URI.class), eq(HttpMethod.GET), any(HttpEntity.class), eq(RejisCaseList.class)))
        .thenReturn(caseListResponse);
		when(restTemplate.exchange(any(URI.class), eq(HttpMethod.GET), any(HttpEntity.class), eq(RejisFullCitation.class)))
        .thenReturn(fullCaseResponse);
		when(mockRejisConfiguration.getRootUrl()).thenReturn("http://www.myUrl.com");
		when(mockCitationTransformer.fromRejisFullCitation(any(RejisFullCitation.class), any(RejisPartialCitation.class)))
				.thenReturn(CITATION);
		when(mockCitationFilter.Filter(any(List.class), any(LocalDate.class), anyString()))
		.thenReturn(Arrays.asList(CITATION));
		
		List<Citation> returnedCitation = mockRejisCitationDataSource.getByNameAndMunicipalitiesAndDOB(NAME,MUNICIPALITIES, DOB);
		
		assertThat(returnedCitation.get(0).citation_number, is("1234"));
	}
	
	
	@SuppressWarnings("unchecked")
	@Test
	public void returnsEmptyCitationsOnRejisCaseListRestTemplateError(){
		final List<String> MUNICIPALITYCODES = Lists.newArrayList("A","B","C");
		when(mockRejisConfiguration.getRootUrl()).thenReturn("http://www.myUrl.com");
		
		when(mockRejisMunicipalityCodesFactory.getAllMunicipalityCodes()).thenReturn(MUNICIPALITYCODES);
		when(restTemplate.exchange(any(URI.class), eq(HttpMethod.GET), any(HttpEntity.class), eq(RejisCaseList.class)))
		.thenThrow(RestClientException.class);
		
		List<Citation> returnedCitation = mockRejisCitationDataSource.getByCitationNumberAndDOB("123", LocalDate.parse("1996-01-02"));
		
		assertThat(returnedCitation.size(), is(0));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void returnsEmptyCitationsOnRejisFullCaseRestTemplateError(){
		final List<String> MUNICIPALITYCODES = Lists.newArrayList("A","B","C");
		
		when(mockRejisMunicipalityCodesFactory.getAllMunicipalityCodes()).thenReturn(MUNICIPALITYCODES);
		when(restTemplate.exchange(any(URI.class), eq(HttpMethod.GET), any(HttpEntity.class), eq(RejisCaseList.class)))
        	.thenReturn(caseListResponse);
		when(restTemplate.exchange(any(URI.class), eq(HttpMethod.GET), any(HttpEntity.class), eq(RejisFullCitation.class)))
			.thenThrow(RestClientException.class);
		when(mockRejisConfiguration.getRootUrl()).thenReturn("http://www.myUrl.com");
			
		
		List<Citation> returnedCitation = mockRejisCitationDataSource.getByCitationNumberAndDOB("123", LocalDate.parse("1996-01-02"));
		
		assertThat(returnedCitation.size(), is(0));
	}
	
}
