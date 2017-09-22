package svc.data.citations.datasources.rejis;

import com.google.common.collect.Lists;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import svc.data.citations.datasources.CITATION_DATASOURCE;
import svc.data.jdbc.BaseJdbcDao;
import svc.logging.LogSystem;

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
            parameterMap.put("datasource", CITATION_DATASOURCE.REJIS.toString());
            String sql = getSql("municipality/get-datasourceMunicipalityIdentifier-from-municipalityId.sql");

            if(municipalityIds.size() > 0) {
                StringJoiner joiner  = new StringJoiner(",");
                for(Long id : municipalityIds) { joiner.add(id.toString()); }
                parameterMap.put("municipalities", joiner.toString());

                sql += " AND dmm.municipality_id IN("+joiner.toString()+")";
            }
           
            municipalityCodes = jdbcTemplate.query(sql, parameterMap, new MunicipalityCodesMapper());
        } catch (Exception e) {
            LogSystem.LogDBException(e);
        }

        return municipalityCodes;
    }

    private class MunicipalityCodesMapper implements RowMapper<String> {
        public String mapRow(ResultSet rs, int i){
            String municipalityCode = null;
            try {
            	municipalityCode = rs.getString("datasource_municipality_identifier");
            } catch (Exception e) {
                LogSystem.LogDBException(e);
            }

            return municipalityCode;
        }
    }
}
