package svc.data.citations.datasources.tyler.transformers;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.core.io.ResourceLoader.CLASSPATH_URL_PREFIX;

import java.io.IOException;

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
import svc.models.Municipality;
import svc.types.HashableEntity;

@RunWith(MockitoJUnitRunner.class)
public class MunicipalityIdTransformerTest {
	@InjectMocks
	MunicipalityIdTransformer mockMunicipalityIdTransformer;
	
	@Mock
    private ResourceLoader resourceLoader;
	
	@Mock
	NamedParameterJdbcTemplate mockJdbcTemplate;
	
	@SuppressWarnings("unchecked")
	@Test
	public void returnsMunicipalityIdFromTylerCourtIdentifier() throws IOException{
		Resource resource = mock(Resource.class);
        when(resource.getInputStream()).thenReturn(null);
        when(resourceLoader.getResource(CLASSPATH_URL_PREFIX + "sql/municipality/get-municipalityId-from-datasource.sql")).thenReturn(resource);

		
		when(mockJdbcTemplate.queryForObject(anyString(),anyMap(),Matchers.<RowMapper<Long>>any())).thenReturn(5L);
		
		HashableEntity<Municipality> returnedMunicipalityId = mockMunicipalityIdTransformer.lookupMunicipalityId(CITATION_DATASOURCE.TYLER,"someIdentifier");
		assertThat(returnedMunicipalityId.getValue(),is(5L));
	}

}
