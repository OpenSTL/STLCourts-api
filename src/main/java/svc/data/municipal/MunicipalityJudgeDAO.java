package svc.data.municipal;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import svc.data.jdbc.BaseJdbcDao;
import svc.logging.LogSystem;
import svc.models.MunicipalityJudge;

@Repository
public class MunicipalityJudgeDAO extends BaseJdbcDao {
	public List<MunicipalityJudge> getByCourtId(int courtId){
		try{
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("courtId", courtId);
			String sql = "SELECT * FROM municipality_judges WHERE court_id = :courtId";
			List<MunicipalityJudge> municipalityJudges = jdbcTemplate.query(sql,parameterMap, new MunicipalityJudgeSQLMapper());
			if (municipalityJudges == null)
				municipalityJudges = new ArrayList();
			return municipalityJudges;
		}catch (Exception e){
			return null;
		}
	}
	
	public MunicipalityJudge getByMunicipalityJudgeId(int municipalityJudgeId){
		try{
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("municipalityJudgeId", municipalityJudgeId);
			String sql = "SELECT * FROM municipality_judges WHERE id = :municipalityJudgeId";
			MunicipalityJudge municipalityJudge = jdbcTemplate.queryForObject(sql, parameterMap, new MunicipalityJudgeSQLMapper());
			return municipalityJudge;
		}catch (Exception e){
			return null;
		}
	}
	
	
	private class MunicipalityJudgeSQLMapper implements RowMapper<MunicipalityJudge> {
		public MunicipalityJudge mapRow(ResultSet rs, int i) {
			MunicipalityJudge municipalityJudge = new MunicipalityJudge();
			try {	
				municipalityJudge.id = rs.getInt("id");
				municipalityJudge.judge = rs.getString("judge");
				municipalityJudge.court_id = rs.getInt("court_id");
			} catch (Exception e) {
				LogSystem.LogDBException(e);
				return null;
			}
			
			return municipalityJudge;
		}
	}
}