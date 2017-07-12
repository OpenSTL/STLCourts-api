package svc.data.citations.datasources.rejis;

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
public class RejisMunicipalityCodesFactory extends BaseJdbcDao {
    
    public List<String> getAllMunicipalityCodes() {
        return getMunicipalityCodes();
    }

    public List<String> getMunicipalityCodesForMunicipalities(List<Long> municipalityIds) {
        return getMunicipalityCodes(municipalityIds);
    }

    private List<String> getMunicipalityCodes() {
        return getMunicipalityCodes(Lists.newArrayList());
    }

    private List<String> getMunicipalityCodes(List<Long> municipalityIds) {
        List<String> municipalityCodes = new ArrayList<>();
        try  {
            Map<String, Object> parameterMap = new HashMap<>();

            String sql = getSql("citation/datasources/get-all.sql");

            if(municipalityIds.size() > 0) {
                //NOTE: The following 2 lines could be 1 if we use Groovy
                StringJoiner joiner  = new StringJoiner(",");
                for(Long id : municipalityIds) { joiner.add(id.toString()); }
                parameterMap.put("municipalities", joiner.toString());

                sql += " WHERE cdm.municipality_id IN(:municipalities)";
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
