package svc.data.municipal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.core.io.ResourceLoader.CLASSPATH_URL_PREFIX;

@RunWith(MockitoJUnitRunner.class)
public class MunicipalityDAOTest{
	@InjectMocks
	private MunicipalityDAO municipalityDAO;
	
	@Mock
    private ResourceLoader resourceLoader;

	@Captor
    ArgumentCaptor<String> sqlCaptor;

	@Captor
    ArgumentCaptor<Map> paramMapCaptor;
	
	@Mock
    private NamedParameterJdbcTemplate jdbcTemplate;
		
	@SuppressWarnings("unchecked")
	@Test
	public void returnsMunicipalitiesFromCourtId() throws IOException {
		final long COURTID = 123458L;

        Resource resource = mock(Resource.class);
        when(resource.getInputStream()).thenReturn(null);
        when(resourceLoader.getResource(CLASSPATH_URL_PREFIX + "sql/municipality/get-all.sql")).thenReturn(resource);

		municipalityDAO.getByCourtId(COURTID);

        verify(jdbcTemplate).query(sqlCaptor.capture(), paramMapCaptor.capture(), Matchers.<RowCallbackHandler>any());
        assertTrue(sqlCaptor.getValue().contains("WHERE mc.court_id = :courtId"));
        assertEquals(paramMapCaptor.getValue().get("courtId"), COURTID);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void returnsMunicipalityFromMunicipalityId() throws IOException {
		final Long MUNICIPALITYID = 123458L;

        Resource resource = mock(Resource.class);
        when(resource.getInputStream()).thenReturn(null);
        when(resourceLoader.getResource(CLASSPATH_URL_PREFIX + "sql/municipality/get-all.sql")).thenReturn(resource);

		municipalityDAO.getByMunicipalityId(MUNICIPALITYID);

        verify(jdbcTemplate).query(sqlCaptor.capture(), paramMapCaptor.capture(), Matchers.<RowCallbackHandler>any());
        assertTrue(sqlCaptor.getValue().contains("WHERE m.municipality_id = :municipalityId"));
        assertEquals(paramMapCaptor.getValue().get("municipalityId"), MUNICIPALITYID);
	}
	
	@Test
	public void returnsAllMunicipalities() throws IOException {
        Resource resource = mock(Resource.class);
        when(resource.getInputStream()).thenReturn(null);
        when(resourceLoader.getResource(CLASSPATH_URL_PREFIX + "sql/municipality/get-all.sql")).thenReturn(resource);

		municipalityDAO.getAllMunicipalities();

        verify(jdbcTemplate).query(Matchers.anyString(), Matchers.any(RowCallbackHandler.class));
	}
}
