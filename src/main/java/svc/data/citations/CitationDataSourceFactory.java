package svc.data.citations;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import svc.data.citations.datasources.CITATION_DATASOURCE;
import svc.data.citations.datasources.mock.MockCitationDataSource;
import svc.data.citations.datasources.tyler.TylerCitationDataSource;
import svc.data.jdbc.BaseJdbcDao;
import svc.logging.LogSystem;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

@Component
public final class CitationDataSourceFactory extends BaseJdbcDao {
    @Value("${stlcourts.citationDataSources.testEnabled}")
    Boolean testCitationSourcesEnabled;

    @Value("${stlcourts.citationDataSources.liveEnabled}")
    Boolean liveCitationSourcesEnabled;

    private static final CitationDataSource mockSource = new MockCitationDataSource();
    private static final CitationDataSource tylerSource = new TylerCitationDataSource();

    public List<CitationDataSource> getAllCitationDataSources() {
        return Lists.newArrayList(getEnabledSources());
    }

    public List<CitationDataSource> getCitationDataSourcesForMunicipalities(List<Long> municipalityIds) {
        List<CITATION_DATASOURCE> sourcesForMunicipalities = getCitationDataSourceNames(municipalityIds);

        //TODO: MOck

        List<CitationDataSource> dataSources = new ArrayList<>();

        for(CITATION_DATASOURCE source : sourcesForMunicipalities) {
            switch(source) {
                case TYLER:
                    dataSources.add(tylerSource);
                default:
                    LogSystem.LogCitationDataSourceException("Source '" + source.toString() + "' is not supported");
            }
        }

        return dataSources;
    }

    private List<CitationDataSource> getEnabledSources() {
        List<CitationDataSource> sources = Lists.newArrayList();

        if(testCitationSourcesEnabled) {
            sources.add(mockSource);
        }

        if(liveCitationSourcesEnabled) {
            sources.add(tylerSource);
        }

        return sources;
    }

    private List<CITATION_DATASOURCE> getCitationDataSourceNames(List<Long> municipalityIds) {
        List<CITATION_DATASOURCE> sourceNames = new ArrayList<>();
        try  {
            //NOTE: The following 2 lines could be 1 if we use Groovy
            StringJoiner joiner  = new StringJoiner(",");
            for(Long id : municipalityIds) { joiner.add(id.toString()); }
            String sql = "SELECT cd.name FROM citation_datasource_municipality cdm INNER JOIN citation_datasource cd ON cd.id = cdm.citation_datasource_id WHERE cd.id IN(" + joiner.toString() + ")";
            sourceNames = jdbcTemplate.query(sql, new CitationDataSourceMapper());
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
