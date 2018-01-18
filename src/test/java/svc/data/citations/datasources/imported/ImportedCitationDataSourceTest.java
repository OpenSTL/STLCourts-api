package svc.data.citations.datasources.imported;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;


import com.google.common.collect.Lists;

import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import svc.data.citations.datasources.imported.models.ImportedCitation;
import svc.data.citations.datasources.imported.transformers.ImportedCitationTransformer;
import svc.data.citations.datasources.transformers.CourtIdTransformer;
import svc.data.citations.filters.CitationFilter;
import svc.models.Citation;

@RunWith(MockitoJUnitRunner.class)
public class ImportedCitationDataSourceTest {
	@InjectMocks
	ImportedCitationDataSource mockImportedCitationDataSource;
	
	@Mock
	ImportedConfiguration mockImportedConfiguration;
	@Mock
    UriComponentsBuilder mockUriComponentsBuilder;
	@Mock
    RestTemplate restTemplate;
	@Mock
	ImportedCitationTransformer mockCitationTransformer;
	@Mock
	CitationFilter mockCitationFilter;
	@Mock
	CourtIdTransformer mockCourtIdTransformer;
	
	@Spy
	ResponseEntity<List<ImportedCitation>> importedCitationsResponseSpy = new ResponseEntity<List<ImportedCitation>>(HttpStatus.ACCEPTED);
    
	@SuppressWarnings("unchecked")
	@Test
	public void returnsCitationsGivenCitationNumberAndDOB(){
		final String CITATIONNUMBER = "F3453";
		final LocalDate DOB = LocalDate.parse("2000-06-01");
		mockImportedConfiguration.rootUrl = "http://myURL.com";
		final Citation CITATION = new Citation();
        CITATION.id = 3;
        final List<Citation> CITATIONS = Lists.newArrayList(CITATION);
        final List<ImportedCitation> importedCitations = Lists.newArrayList();
        Mockito.doReturn(importedCitations).when(importedCitationsResponseSpy).getBody();
        
        when(restTemplate.exchange(any(URI.class), eq(HttpMethod.GET), any(HttpEntity.class), any(ParameterizedTypeReference.class)))
        .thenReturn(importedCitationsResponseSpy);
        
        when(mockCitationTransformer.fromImportedCitations(importedCitations)).thenReturn(CITATIONS);
        when(mockCitationFilter.Filter(CITATIONS, null)).thenReturn(CITATIONS);
        
		List<Citation> citations = mockImportedCitationDataSource.getByCitationNumberAndDOB(CITATIONNUMBER, DOB);
		
		assertThat(citations.get(0).id, is(3));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void returnsCitationsGivenLicenseAndDOB(){
		final String DRIVERSLICENSENUMBER = "ABCDE";
		final String DRIVERSLICENSESTATE = "AZ";
		final LocalDate DOB = LocalDate.parse("2000-06-01");
		final String LASTNAME = "someName";
		mockImportedConfiguration.rootUrl = "http://myURL.com";
		
		final Citation CITATION = new Citation();
        CITATION.id = 3;
        final List<Citation> CITATIONS = Lists.newArrayList(CITATION);
        final List<ImportedCitation> importedCitations = Lists.newArrayList();
        Mockito.doReturn(importedCitations).when(importedCitationsResponseSpy).getBody();
        
        when(restTemplate.exchange(any(URI.class), eq(HttpMethod.GET), any(HttpEntity.class), any(ParameterizedTypeReference.class)))
        .thenReturn(importedCitationsResponseSpy);
        
        when(mockCitationTransformer.fromImportedCitations(importedCitations)).thenReturn(CITATIONS);
        when(mockCitationFilter.Filter(CITATIONS, LASTNAME)).thenReturn(CITATIONS);
        
		List<Citation> citations = mockImportedCitationDataSource.getByLicenseAndDOBAndLastName(DRIVERSLICENSENUMBER,DRIVERSLICENSESTATE, DOB, LASTNAME);
		
		assertThat(citations.get(0).id, is(3));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void returnsCitationsGivenNameAndMunicipalitiesAndDOB(){
		final String NAME = "Smith";
		final List<Long> MUNICIPALITIES = Lists.newArrayList(5L);
		final LocalDate DOB = LocalDate.parse("2000-06-01");
		mockImportedConfiguration.rootUrl = "http://myURL.com";
		
		final Citation CITATION = new Citation();
        CITATION.id = 3;
        final List<Citation> CITATIONS = Lists.newArrayList(CITATION);
        final List<ImportedCitation> importedCitations = Lists.newArrayList();
        Mockito.doReturn(importedCitations).when(importedCitationsResponseSpy).getBody();
        
        final List<Long> COURTIDS = Lists.newArrayList(1L);
        when(mockCourtIdTransformer.getCourtIdsFromMunicipalityIds(MUNICIPALITIES)).thenReturn(COURTIDS);
        
        when(restTemplate.exchange(any(URI.class), eq(HttpMethod.GET), any(HttpEntity.class), any(ParameterizedTypeReference.class)))
        .thenReturn(importedCitationsResponseSpy);
        
        when(mockCitationTransformer.fromImportedCitations(importedCitations)).thenReturn(CITATIONS);
        when(mockCitationFilter.Filter(CITATIONS,NAME)).thenReturn(CITATIONS);
        
		List<Citation> citations = mockImportedCitationDataSource.getByNameAndMunicipalitiesAndDOB(NAME,MUNICIPALITIES,DOB);
		
		assertThat(citations.get(0).id, is(3));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void returnsEmptyCitationsOnRestTemplateError(){
		final String CITATIONNUMBER = "F3453";
		final LocalDate DOB = LocalDate.parse("2000-06-01");
		mockImportedConfiguration.rootUrl = "http://myURL.com";
		
		final Citation CITATION = new Citation();
        CITATION.id = 3;
 
        when(restTemplate.exchange(any(URI.class), eq(HttpMethod.GET), any(HttpEntity.class), any(ParameterizedTypeReference.class)))
        .thenThrow(RestClientException.class);
        
		List<Citation> citations = mockImportedCitationDataSource.getByCitationNumberAndDOB(CITATIONNUMBER, DOB);
		
		assertThat(citations.size(), is(0));
	}
	
}
