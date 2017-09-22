package svc.data.citations.datasources.rejis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.core.io.ResourceLoader.CLASSPATH_URL_PREFIX;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import svc.data.citations.datasources.CITATION_DATASOURCE;

@RunWith(MockitoJUnitRunner.class)
public class RejisMunicipalityCodesFactoryTest {
	@InjectMocks
	RejisMunicipalityCodesFactory municipalityCodesFactory;

	@Mock
	private ResourceLoader resourceLoader;

	@Mock
	NamedParameterJdbcTemplate jdbcTemplate;
	
	@Captor
	ArgumentCaptor<String> sqlCaptor;

	@Captor
	ArgumentCaptor<Map<String, ?>> paramMapCaptor;

	@SuppressWarnings("unchecked")
	@Test
	public void getsAllMunicipalityCodes() throws IOException {
		final List<String> municipalityCodes = Lists.newArrayList("A","B","C");

		Resource resource = mock(Resource.class);
		when(resource.getInputStream()).thenReturn(null);
		when(resourceLoader.getResource(CLASSPATH_URL_PREFIX + "sql/municipality/get-datasourceMunicipalityIdentifier-from-municipalityId.sql")).thenReturn(resource);

		when(jdbcTemplate.query(Matchers.anyString(), Matchers.anyMap(), Matchers.<RowMapper<String>> any())).thenReturn(municipalityCodes);

		List<String> codes = municipalityCodesFactory.getAllMunicipalityCodes();

		assertEquals(codes.size(), 3);
	}
	
	@Test
	public void getsMunicipalityCodesForMunicipalities() throws IOException {
		final List<Long> municipalityIds = Lists.newArrayList(5L,2L);
		final List<String> municipalityCodes = Lists.newArrayList("A","B","C");

		Resource resource = mock(Resource.class);
		when(resource.getInputStream()).thenReturn(null);
		when(resourceLoader.getResource(CLASSPATH_URL_PREFIX + "sql/municipality/get-datasourceMunicipalityIdentifier-from-municipalityId.sql")).thenReturn(resource);

		when(jdbcTemplate.query(sqlCaptor.capture(), paramMapCaptor.capture(), Matchers.<RowMapper<String>> any())).thenReturn(municipalityCodes);

		List<String> codes = municipalityCodesFactory.getMunicipalityCodesForMunicipalities(municipalityIds);

		assertTrue(sqlCaptor.getValue().contains("AND dmm.municipality_id IN(5,2)"));
		assertEquals(paramMapCaptor.getValue().get("datasource"), CITATION_DATASOURCE.REJIS.toString());
		assertEquals(paramMapCaptor.getValue().get("municipalities"), "5,2");
		assertEquals(codes.size(), 3);
	}
}
