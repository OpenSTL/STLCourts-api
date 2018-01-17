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
import java.util.stream.Collectors;

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
    
    @Inject
    private CitationDataSource rejisCitationDataSource;
    
    @Inject
    private CitationDataSource importedCitationDataSource;

    public List<CitationDataSource> getAllCitationDataSources() {
        return getDataSources();
    }

    public List<CitationDataSource> getCitationDataSourcesForMunicipalities(List<Long> municipalityIds) {
        return getDataSources(municipalityIds);
    }

    private List<CitationDataSource> getDataSources() {
        return getDataSources(Lists.newArrayList());
    }

    private List<CitationDataSource> getDataSources(List<Long> municipalityIds) {
        List<CITATION_DATASOURCE> sourcesNames = Lists.newArrayList();

        if(testCitationSourcesEnabled) {
            sourcesNames.add(CITATION_DATASOURCE.MOCK);
        }

        if(liveCitationSourcesEnabled) {
            sourcesNames.addAll(getCitationDataSourceNames(municipalityIds));
        }

        return getSourcesFromNames(sourcesNames);
    }

    private List<CitationDataSource> getSourcesFromNames(List<CITATION_DATASOURCE> sourceNames) {
        List<CitationDataSource> dataSources = Lists.newArrayList();

        for(CITATION_DATASOURCE source : sourceNames) {
            switch(source) {
                case MOCK:
                    dataSources.add(mockCitationDataSource);
                    break;
                case TYLER:
                    dataSources.add(tylerCitationDataSource);
                    break;
                case REJIS:
                	dataSources.add(rejisCitationDataSource);
                	break;
                case IMPORTED:
                	dataSources.add(importedCitationDataSource);
                	break;
                default:
                    LogSystem.LogCitationDataSourceException("Source '" + source.toString() + "' is not supported");
            }
        }

        return dataSources;
    }

    private List<CITATION_DATASOURCE> getCitationDataSourceNames(List<Long> municipalityIds) {
        List<CITATION_DATASOURCE> sourceNames = new ArrayList<>();
        try  {
            Map<String, Object> parameterMap = new HashMap<>();

            String sql = getSql("citation/datasources/get-all.sql");

            if(municipalityIds.size() > 0) {
                //NOTE: The following 2 lines could be 1 if we use Groovy
            	List<String> municipalityIds_string = municipalityIds.stream().map(Object::toString).collect(Collectors.toList());
                sql += " WHERE cdm.municipality_id IN("+String.join(",",municipalityIds_string)+")";
            }
            sourceNames = jdbcTemplate.query(sql, parameterMap, new CitationDataSourceMapper());
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
