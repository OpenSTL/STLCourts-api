package svc.data.citations.datasources.transformers;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.core.io.ResourceLoader.CLASSPATH_URL_PREFIX;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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

import svc.data.citations.datasources.CITATION_DATASOURCE;
import svc.models.Court;
import svc.types.HashableEntity;

@RunWith(MockitoJUnitRunner.class)
public class CourtIdTransformerTest {
	@InjectMocks
	CourtIdTransformer mockCourtIdTransformer;
	
	@Mock
    private ResourceLoader resourceLoader;
	
	@Mock
	NamedParameterJdbcTemplate mockJdbcTemplate;
	
	@SuppressWarnings("unchecked")
	@Test
	public void returnsCourtIdFromTylerCourtIdentifier() throws IOException{
		Resource resource = mock(Resource.class);
        when(resource.getInputStream()).thenReturn(null);
        when(resourceLoader.getResource(CLASSPATH_URL_PREFIX + "sql/citation/datasources/transformers/tyler-courtTransformer.sql")).thenReturn(resource);
        
		when(mockJdbcTemplate.queryForObject(anyString(),anyMap(),Matchers.<RowMapper<Long>>any())).thenReturn(5L);
		
		HashableEntity<Court> returnedCourtId = mockCourtIdTransformer.lookupCourtId(CITATION_DATASOURCE.TYLER,"someIdentifier");
		assertThat(returnedCourtId.getValue(),is(5L));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void returnsCourtIdFromRejisCourtIdentifier() throws IOException{
		Resource resource = mock(Resource.class);
        when(resource.getInputStream()).thenReturn(null);
        when(resourceLoader.getResource(CLASSPATH_URL_PREFIX + "sql/citation/datasources/transformers/rejis-courtTransformer.sql")).thenReturn(resource);
        
		when(mockJdbcTemplate.queryForObject(anyString(),anyMap(),Matchers.<RowMapper<Long>>any())).thenReturn(5L);
		
		HashableEntity<Court> returnedCourtId = mockCourtIdTransformer.lookupCourtId(CITATION_DATASOURCE.REJIS,"someIdentifier");
		assertThat(returnedCourtId.getValue(),is(5L));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void returnsCourtIdsFromMunicipalityIds() throws IOException{
		Resource resource = mock(Resource.class);
        when(resource.getInputStream()).thenReturn(null);
        when(resourceLoader.getResource(CLASSPATH_URL_PREFIX + "sql/court/get-courtIds-from-municipalityIds.sql")).thenReturn(resource);
        
         final List<Long> COURTIDS = Arrays.asList(5L,6L,7L);
		when(mockJdbcTemplate.query(anyString(),anyMap(),Matchers.<RowMapper<Long>>any())).thenReturn(COURTIDS);
		
		List<Long> returnedCourtIds = mockCourtIdTransformer.getCourtIdsFromMunicipalityIds(Arrays.asList(1L,2L,3L));
		assertEquals(COURTIDS,returnedCourtIds);
	}
	
	public void returnsNullWhenInvalidCitationDatasource(){
		
		HashableEntity<Court> returnedCourtId = mockCourtIdTransformer.lookupCourtId(CITATION_DATASOURCE.MOCK,"someIdentifier");
		assertNull(returnedCourtId);
	}

}
