package svc.data.municipal;

import com.google.common.collect.Lists;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;
import svc.data.jdbc.BaseJdbcDao;
import svc.logging.LogSystem;
import svc.models.Court;
import svc.models.Judge;
import svc.types.HashableEntity;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CourtDAO extends BaseJdbcDao {
	public static final String COURT_ID_COLUMN_NAME = "court_id";
	public static final String COURT_NAME_COLUMN_NAME = "court_name";
    public static final String COURT_PHONE_COLUMN_NAME = "phone";
    public static final String COURT_PHONE_EXTENSION_COLUMN_NAME = "extension";
    public static final String COURT_WEBSITE_COLUMN_NAME = "website";
    public static final String COURT_PAYMENT_SYSTEM_COLUMN_NAME = "payment_system";
    public static final String COURT_ADDRESS_COLUMN_NAME = "address";
    public static final String COURT_CITY_COLUMN_NAME = "city";
    public static final String COURT_STATE_COLUMN_NAME = "state";
    public static final String COURT_ZIP_COLUMN_NAME = "zip_code";
    public static final String COURT_LATITUDE_COLUMN_NAME = "latitude";
    public static final String COURT_LONGITUDE_COLUMN_NAME = "longitude";
	
	public Court getCourtById(Long courtId){
		try{
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("courtId", courtId);
            String sql = getSql("court/get-all.sql") + " WHERE c.court_id = :courtId";

            CourtRowCallbackHandler courtRowCallbackHandler = new CourtRowCallbackHandler();
            jdbcTemplate.query(sql, parameterMap, courtRowCallbackHandler);

            return courtRowCallbackHandler.courtMap.values().iterator().next(); //Should only be 1
		}catch (Exception e){
            LogSystem.LogDBException(e);
			return null;
		}
	}
	
	public List<Court> getAllCourts() {
		try  {
            CourtRowCallbackHandler courtRowCallbackHandler = new CourtRowCallbackHandler();
            String sql = getSql("court/get-all.sql") + " ORDER BY c.court_name";
            jdbcTemplate.query(sql, courtRowCallbackHandler);

			return Lists.newArrayList(courtRowCallbackHandler.courtMap.values());
		} catch (Exception e) {
			LogSystem.LogDBException(e);
			return null;
		}
	}

	public List<Court> getCourtsByMunicipalityId(Long municipalityId) {
        try {
            Map<String, Object> parameterMap = new HashMap<>();
            parameterMap.put("municipalityId", municipalityId);

            String sql = getSql("court/get-all.sql") + " WHERE mc.municipality_id = :municipalityId ORDER BY c.court_name";
            CourtRowCallbackHandler courtRowCallbackHandler = new CourtRowCallbackHandler();
            jdbcTemplate.query(sql, parameterMap, courtRowCallbackHandler);

            return Lists.newArrayList(courtRowCallbackHandler.courtMap.values());
        } catch (Exception e) {
            LogSystem.LogDBException(e);
            return null;
        }
    }

    private final class CourtRowCallbackHandler implements RowCallbackHandler {
        public Map<Long, Court> courtMap = new HashMap<>();

        @Override
        public void processRow(ResultSet rs) throws SQLException {
            try {
            	Long courtId = rs.getLong(COURT_ID_COLUMN_NAME);
                Judge judge = buildJudge(rs);
                if (courtMap.containsKey(courtId)){
                    courtMap.get(courtId).judges.add(judge);
                }else{
                    Court court = buildCourt(rs);
                    court.judges.add(judge);
                    courtMap.put(courtId, court);
                }
            } catch (Exception e) {
                LogSystem.LogDBException(e);
            }
        }

        private Court buildCourt(ResultSet rs) throws SQLException {
            Court court = new Court();
            court.id = new HashableEntity<Court>(Court.class,rs.getLong(COURT_ID_COLUMN_NAME));
            court.name = rs.getString(COURT_NAME_COLUMN_NAME);
            court.phone = rs.getString(COURT_PHONE_COLUMN_NAME).replaceAll("[.\\- ]", ".");
            if (!court.phone.equals("")){
                String[] phoneParts = court.phone.split("\\.");
                court.phone = "("+phoneParts[0]+") "+phoneParts[1]+"-"+phoneParts[2];
            }
            court.extension = rs.getString(COURT_PHONE_EXTENSION_COLUMN_NAME);
            court.website = rs.getString(COURT_WEBSITE_COLUMN_NAME);
            court.paymentSystem = rs.getString(COURT_PAYMENT_SYSTEM_COLUMN_NAME);
            court.address = rs.getString(COURT_ADDRESS_COLUMN_NAME);
            court.city = rs.getString(COURT_CITY_COLUMN_NAME);
            court.state = rs.getString(COURT_STATE_COLUMN_NAME);
            court.zip = rs.getString(COURT_ZIP_COLUMN_NAME);
            court.latitude = new BigDecimal(rs.getString(COURT_LATITUDE_COLUMN_NAME));
            court.longitude = new BigDecimal(rs.getString(COURT_LONGITUDE_COLUMN_NAME));
            court.judges = Lists.newArrayList();

            return court;
        }

        private Judge buildJudge(ResultSet rs) throws SQLException {
            Judge judge = new Judge();
            judge.id = new HashableEntity<Judge>(Judge.class,rs.getLong("JUDGE_ID"));
            judge.judge = rs.getString(rs.findColumn("judge"));
            judge.court_id = new HashableEntity<Court>(Court.class,rs.getLong("JUDGES_COURT_ID"));
            return judge;
        }
    }
}
