package svc.data.citations;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import svc.data.citations.datasources.CITATION_DATASOURCE;
import svc.data.jdbc.BaseJdbcDao;
import svc.logging.LogSystem;

import javax.inject.Inject;
import java.sql.ResultSet;
import java.util.*;

@Component
public class CitationDataSourceFactory extends BaseJdbcDao {
    @Value("${stlcourts.citationDataSources.testEnabled}")
    Boolean testCitationSourcesEnabled;

    @Value("${stlcourts.citationDataSources.liveEnabled}")
    Boolean liveCitationSourcesEnabled;

    @Inject
    private CitationDataSource mockCitationDataSource;

    @Inject
    private CitationDataSource tylerCitationDataSource;

    public List<CitationDataSource> getAllCitationDataSources() {
        return Lists.newArrayList(getEnabledSources());
    }

    public List<CitationDataSource> getCitationDataSourcesForMunicipalities(List<Long> municipalityIds) {
        List<CITATION_DATASOURCE> sourcesForMunicipalities = Lists.newArrayList();

        if(liveCitationSourcesEnabled) {
            sourcesForMunicipalities.addAll(getCitationDataSourceNames(municipalityIds));
        }

        if(testCitationSourcesEnabled) {
            sourcesForMunicipalities.add(CITATION_DATASOURCE.MOCK);
        }

        List<CitationDataSource> dataSources = new ArrayList<>();

        for(CITATION_DATASOURCE source : sourcesForMunicipalities) {
            switch(source) {
                case MOCK:
                    dataSources.add(mockCitationDataSource);
                    break;
                case TYLER:
                    dataSources.add(tylerCitationDataSource);
                    break;
                default:
                    LogSystem.LogCitationDataSourceException("Source '" + source.toString() + "' is not supported");
            }
        }

        return dataSources;
    }

    private List<CitationDataSource> getEnabledSources() {
        List<CitationDataSource> sources = Lists.newArrayList();

        if(testCitationSourcesEnabled) {
            sources.add(mockCitationDataSource);
        }

        if(liveCitationSourcesEnabled) {
            sources.add(tylerCitationDataSource);
        }

        return sources;
    }

    private List<CITATION_DATASOURCE> getCitationDataSourceNames(List<Long> municipalityIds) {
        List<CITATION_DATASOURCE> sourceNames = new ArrayList<>();
        try  {
            //NOTE: The following 2 lines could be 1 if we use Groovy
            StringJoiner joiner  = new StringJoiner(",");
            for(Long id : municipalityIds) { joiner.add(id.toString()); }

            Map<String, Object> parameterMap = new HashMap<>();
            parameterMap.put("municipalities", joiner.toString());

            sourceNames = jdbcTemplate.query(getSql("citation/datasources/get-all.sql"), parameterMap, new CitationDataSourceMapper());
        } catch (Exception e) {
            LogSystem.LogDBException(e);
        }

        return sourceNames;
    }

    private class CitationDataSourceMapper implements RowMapper<CITATION_DATASOURCE> {
        public CITATION_DATASOURCE mapRow(ResultSet rs, int i) {
            CITATION_DATASOURCE datasource = null;
            try {
                datasource = CITATION_DATASOURCE.valueOf(rs.getString("name"));
            } catch (Exception e) {
                LogSystem.LogDBException(e);
            }

            return datasource;
        }
    }
}
