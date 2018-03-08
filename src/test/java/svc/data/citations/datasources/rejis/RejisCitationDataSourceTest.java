package svc.data.citations.datasources.rejis;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.net.URISyntaxException;
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

import svc.data.citations.datasources.rejis.models.RejisCaseList;
import svc.data.citations.datasources.rejis.models.RejisPartialCitation;
import svc.data.citations.datasources.rejis.models.RejisQueryObject;
import svc.data.citations.filters.CitationFilter;
import svc.models.Citation;

@RunWith(MockitoJUnitRunner.class)
public class RejisCitationDataSourceTest {
	@InjectMocks
	RejisCitationDataSource mockRejisCitationDataSource;
	
	@Mock
	RejisApiCalls mockRejisApiCalls;
	
	@Mock
	RejisUriBuilder mockRejisUriBuilder;
	
	@Mock
	RejisMunicipalityCodesFactory mockRejisMunicipalityCodesFactory;
	
	@Mock
	RejisCitationFilter mockRejisCitationFilter;
	
	@Mock
	CitationFilter mockCitationFilter;
	
	final RejisCaseList rejisCaseList = new RejisCaseList();
	
	@Before
	public void init(){	
		
		rejisCaseList.totalPages = 1;
		rejisCaseList.pageNumber = 1;
		final RejisPartialCitation rejisPartialCitation = new RejisPartialCitation();
		rejisPartialCitation.caseNumber = "123";
		rejisPartialCitation.caseStatus = "A";
		rejisCaseList.caseIndexRows = Lists.newArrayList(rejisPartialCitation);
		
	}
    
	@SuppressWarnings("unchecked")
	@Test
	public void returnsCitationsGivenCitationNumberAndDOB() throws URISyntaxException{
		final List<String> MUNICIPALITYCODES = Lists.newArrayList("A","B","C");
		final Citation CITATION = new Citation();
		CITATION.citation_number = "1234";
		
		List<Citation> CITATIONS = Arrays.asList(CITATION);

		URI uri = URI.create("http://www.google.com");
		
		when(mockRejisMunicipalityCodesFactory.getAllMunicipalityCodes()).thenReturn(MUNICIPALITYCODES);
		when(mockRejisUriBuilder.createURI(any(RejisQueryObject.class))).thenReturn(uri);
		
		when(mockRejisApiCalls.getRejisCaseList(any(URI.class))).thenReturn(rejisCaseList);
		
		when(mockRejisApiCalls.getFullCitations(any())).thenReturn(CITATIONS);
		
		when(mockRejisCitationFilter.Filter(any(List.class))).thenReturn(rejisCaseList.caseIndexRows);
		
		when(mockCitationFilter.Filter(CITATIONS, null))
		.thenReturn(CITATIONS);
		
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
		
		List<Citation> CITATIONS = Arrays.asList(CITATION);
		URI uri = URI.create("http://www.google.com");
		
		when(mockRejisMunicipalityCodesFactory.getAllMunicipalityCodes()).thenReturn(MUNICIPALITYCODES);
		when(mockRejisUriBuilder.createURI(any(RejisQueryObject.class))).thenReturn(uri);
		
		when(mockRejisApiCalls.getRejisCaseList(any(URI.class))).thenReturn(rejisCaseList);
		when(mockRejisCitationFilter.Filter(any(List.class))).thenReturn(rejisCaseList.caseIndexRows);
		
		when(mockRejisApiCalls.getFullCitations(any())).thenReturn(CITATIONS);
		
		when(mockCitationFilter.Filter(CITATIONS, LASTNAME))
		.thenReturn(CITATIONS);
		
		List<Citation> returnedCitation = mockRejisCitationDataSource.getByLicenseAndDOBAndLastName(DRIVERSLICENSENUMBER,DRIVERSLICENSESTATE, DOB,LASTNAME);
		
		assertThat(returnedCitation.get(0).citation_number, is("1234"));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void returnsCitationsGivenNameAndMunicipalitiesAndDOB(){
		final String NAME = "Smith";
		final List<Long> MUNICIPALITIES = Lists.newArrayList(5L);
		final LocalDate DOB = LocalDate.parse("1999-06-01");
		
		final List<String> MUNICIPALITYCODES = Lists.newArrayList("A","B","C");
		
		final Citation CITATION = new Citation();
		CITATION.citation_number = "1234";
		
		List<Citation> CITATIONS = Arrays.asList(CITATION);
		URI uri = URI.create("http://www.google.com");
		
		when(mockRejisMunicipalityCodesFactory.getAllMunicipalityCodes()).thenReturn(MUNICIPALITYCODES);
		when(mockRejisUriBuilder.createURI(any(RejisQueryObject.class))).thenReturn(uri);
		
		when(mockRejisApiCalls.getRejisCaseList(any(URI.class))).thenReturn(rejisCaseList);
		when(mockRejisCitationFilter.Filter(any(List.class))).thenReturn(rejisCaseList.caseIndexRows);
		
		when(mockRejisApiCalls.getFullCitations(any())).thenReturn(CITATIONS);
		
		when(mockCitationFilter.Filter(CITATIONS, NAME))
		.thenReturn(CITATIONS);
		
		List<Citation> returnedCitation = mockRejisCitationDataSource.getByNameAndMunicipalitiesAndDOB(NAME,MUNICIPALITIES, DOB);
		
		assertThat(returnedCitation.get(0).citation_number, is("1234"));
	}
	
}
