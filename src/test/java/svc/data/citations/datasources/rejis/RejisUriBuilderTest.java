package svc.data.citations.datasources.rejis;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


import com.google.common.collect.Lists;

import org.springframework.web.util.UriComponentsBuilder;

import svc.data.citations.datasources.rejis.models.RejisQueryObject;

@RunWith(MockitoJUnitRunner.class)
public class RejisUriBuilderTest {
	
	
	@InjectMocks
	RejisUriBuilder mockRejisUriBuilder;
	
	@Mock
    UriComponentsBuilder mockUriComponentsBuilder;
	
	
	@Mock
	RejisConfiguration mockRejisConfiguration;
	
	@Test
	public void returnsCorrectUriFromTicket(){
		final int pageNumber = 1;
		final String citationNumber = "ABC";
		final LocalDate dob = LocalDate.parse("1996-01-02");
		final List<String> MUNICIPALITYCODES = Lists.newArrayList("A","B","C");
		RejisQueryObject rejisQueryObject = (new RejisQueryObject()).loadForTicket(pageNumber, citationNumber, dob, MUNICIPALITYCODES);
		
		when(mockRejisConfiguration.getRootUrl()).thenReturn("http://www.myUrl.com");
		
		URI uri = mockRejisUriBuilder.createURI(rejisQueryObject);
		
		assertTrue(uri.toString().contains("http://www.myUrl.com/"+RejisConstants.BY_TICKET));
		assertTrue(uri.toString().contains(RejisConstants.TICKET_NUMBER+'='+citationNumber));
		assertTrue(uri.toString().contains(RejisConstants.DOB+'='+dob));
		assertTrue(uri.toString().contains(RejisConstants.AGENCY_ID+'='+"A,B,C"));
		assertTrue(uri.toString().contains(RejisConstants.PAGE_NUMBER+'='+pageNumber));
		assertTrue(uri.toString().contains(RejisConstants.ROWS_PER_PAGE+"=50"));
		assertTrue(uri.toString().contains(RejisConstants.RESULT_FORMAT+"=json"));
	}
	
	@Test
	public void returnsUriComponentsBuilderFromLicense(){
		final int pageNumber = 1;
		final String dlNum = "ABC";
		final String dlState = "MO";
		final String lastName = "SMITH";
		final LocalDate dob = LocalDate.parse("1996-01-02");
		final List<String> MUNICIPALITYCODES = Lists.newArrayList("A","B","C");
		
		RejisQueryObject rejisQueryObject = (new RejisQueryObject()).loadForLicense(pageNumber, dlNum, dlState, lastName, dob, MUNICIPALITYCODES);
		
		when(mockRejisConfiguration.getRootUrl()).thenReturn("http://www.myUrl.com");
		
		URI uri = mockRejisUriBuilder.createURI(rejisQueryObject);
		
		assertTrue(uri.toString().contains("http://www.myUrl.com/"+RejisConstants.BY_VEHICLE_LICENSE));
		assertTrue(uri.toString().contains(RejisConstants.VEHICLE_LIC_NUMBER+'='+dlNum));
		assertTrue(uri.toString().contains(RejisConstants.VEHICLE_LIC_STATE+'='+dlState));
		assertTrue(uri.toString().contains(RejisConstants.DOB+'='+dob));
		assertTrue(uri.toString().contains(RejisConstants.LAST_NAME+'='+lastName));
		assertTrue(uri.toString().contains(RejisConstants.AGENCY_ID+'='+"A,B,C"));
		assertTrue(uri.toString().contains(RejisConstants.PAGE_NUMBER+'='+pageNumber));
		assertTrue(uri.toString().contains(RejisConstants.ROWS_PER_PAGE+"=50"));
		assertTrue(uri.toString().contains(RejisConstants.RESULT_FORMAT+"=json"));
	}
	
	@Test
	public void returnsUriComponentsBuilderFromName(){
		final int pageNumber = 1;
		final String lastName = "SMITH";
		final LocalDate dob = LocalDate.parse("1996-01-02");
		final List<String> MUNICIPALITYCODES = Lists.newArrayList("A","B","C");
		
		RejisQueryObject rejisQueryObject = (new RejisQueryObject()).loadForName(pageNumber, lastName, dob, MUNICIPALITYCODES);
		
		when(mockRejisConfiguration.getRootUrl()).thenReturn("http://www.myUrl.com");
		
		URI uri = mockRejisUriBuilder.createURI(rejisQueryObject);
		
		assertTrue(uri.toString().contains("http://www.myUrl.com/"+RejisConstants.BY_NAME));
		assertTrue(uri.toString().contains(RejisConstants.DOB+'='+dob));
		assertTrue(uri.toString().contains(RejisConstants.LAST_NAME+'='+lastName));
		assertTrue(uri.toString().contains(RejisConstants.AGENCY_ID+'='+"A,B,C"));
		assertTrue(uri.toString().contains(RejisConstants.PAGE_NUMBER+'='+pageNumber));
		assertTrue(uri.toString().contains(RejisConstants.ROWS_PER_PAGE+"=50"));
		assertTrue(uri.toString().contains(RejisConstants.RESULT_FORMAT+"=json"));
	}
    
}
