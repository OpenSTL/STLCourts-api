package svc.data.municipal;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import svc.data.jdbc.BaseJdbcDao;
import svc.logging.LogSystem;
import svc.models.Municipality;

@Repository
public class MunicipalityDAO extends BaseJdbcDao {

	public static final String MUNICIPALITY_ID_COLUMN_NAMER = "municipality_id";
    public static final String MUNICIPALITY_NAME_COLUMN_NAMER = "municipality_name";

	public List<Municipality> getByCourtId(Long courtId){
		try{
			Map<String, Object> parameterMap = new HashMap<>();
			parameterMap.put("courtId", courtId);
			String sql = getSql("municipality/get-all.sql") + " WHERE mc.court_id = :courtId";

            MunicipalityRowCallbackHandler rowCallbackHandler = new MunicipalityRowCallbackHandler();
			jdbcTemplate.query(sql, parameterMap, rowCallbackHandler);

			return Lists.newArrayList(rowCallbackHandler.municipalityMap.values());
		}catch (Exception e){
            LogSystem.LogDBException(e);
			return null;
		}
	}
	
	public Municipality getByMunicipalityId(Long municipalityId){
		try{
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("municipalityId", municipalityId);
            String sql = getSql("municipality/get-all.sql") + " WHERE m.municipality_id = :municipalityId";

            MunicipalityRowCallbackHandler rowCallbackHandler = new MunicipalityRowCallbackHandler();
            jdbcTemplate.query(sql, parameterMap, rowCallbackHandler);

			return rowCallbackHandler.municipalityMap.values().iterator().next(); //Should only be 1
		}catch (Exception e){
            LogSystem.LogDBException(e);
			return null;
		}
	}
	
	public List<Municipality> getAllMunicipalities() {
		try  {
            MunicipalityRowCallbackHandler rowCallbackHandler = new MunicipalityRowCallbackHandler();
            jdbcTemplate.query(getSql("municipality/get-all.sql"), rowCallbackHandler);

            return Lists.newArrayList(rowCallbackHandler.municipalityMap.values());
		} catch (Exception e) {
			LogSystem.LogDBException(e);
			return null;
		}
	}
	
	private final class MunicipalityRowCallbackHandler implements RowCallbackHandler {
	    public Map<Long, Municipality> municipalityMap = new HashMap<>();

	    @Override
        public void processRow(ResultSet rs) {
			try {
                Long municipalityId = rs.getLong(MUNICIPALITY_ID_COLUMN_NAMER);
                Long courtId = rs.getLong(CourtDAO.COURT_ID_COLUMN_NAME);
                if(municipalityMap.containsKey(municipalityId)) {
                    municipalityMap.get(municipalityId).courts.add(courtId);
                } else {
                    Municipality municipality = new Municipality();
                    municipality.id = municipalityId;
                    municipality.name = rs.getString(MUNICIPALITY_NAME_COLUMN_NAMER);
                    municipality.courts = Lists.newArrayList(courtId);

                    municipalityMap.put(municipalityId, municipality);
                }
			} catch (Exception e) {
				LogSystem.LogDBException(e);
			}
		}
	}
}