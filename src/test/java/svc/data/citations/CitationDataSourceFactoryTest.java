package svc.data.citations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.core.io.ResourceLoader.CLASSPATH_URL_PREFIX;

import org.assertj.core.util.Lists;
import org.junit.Before;
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
import org.springframework.test.util.ReflectionTestUtils;
import svc.data.citations.datasources.CITATION_DATASOURCE;

import java.io.IOException;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class CitationDataSourceFactoryTest {
    @InjectMocks
    CitationDataSourceFactory citationDataSourceFactory;

    @Mock
    private ResourceLoader resourceLoader;

    @Mock
    NamedParameterJdbcTemplate jdbcTemplate;

    @Before
    public void setupSystemVars() {
        ReflectionTestUtils.setField(citationDataSourceFactory, "testCitationSourcesEnabled", true);
        ReflectionTestUtils.setField(citationDataSourceFactory, "liveCitationSourcesEnabled", true);
    }

    @Test
    public void getsAllCitationSources() {
        List<CitationDataSource> sources = citationDataSourceFactory.getAllCitationDataSources();

        assertEquals(sources.size(), 2);
    }

    @Test
    public void getsCitationSourcesForMunicipalities() throws IOException {
        List<Long> munis = Lists.newArrayList(10L, 20L);
        final List<CITATION_DATASOURCE> sources = Lists.newArrayList(CITATION_DATASOURCE.TYLER);

        Resource resource = mock(Resource.class);
        when(resource.getInputStream()).thenReturn(null);
        when(resourceLoader.getResource(CLASSPATH_URL_PREFIX + "sql/citation/datasources/get-all.sql")).thenReturn(resource);

        when(jdbcTemplate.query(Matchers.anyString(),Matchers.anyMap(), Matchers.<RowMapper<CITATION_DATASOURCE>>any())).thenReturn(sources);

        List<CitationDataSource> dataSources = citationDataSourceFactory.getCitationDataSourcesForMunicipalities(munis);

        assertEquals(dataSources.size(), 2);
    }
}
