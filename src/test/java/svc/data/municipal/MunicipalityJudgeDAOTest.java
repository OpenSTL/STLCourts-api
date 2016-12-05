package svc.data.municipal;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;

import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import static org.hamcrest.CoreMatchers.*;

import svc.models.MunicipalityJudge;

@RunWith(MockitoJUnitRunner.class)
public class MunicipalityJudgeDAOTest{
	@InjectMocks
	MunicipalityJudgeDAO municipalityJudgeDAO;
	
	@Mock
	NamedParameterJdbcTemplate jdbcTemplate;
	
	@SuppressWarnings("unchecked")
	@Test
	public void returnsMunicipalityJudgesFromCourtId(){
		final MunicipalityJudge MUNIJUDGE = new MunicipalityJudge();
		MUNIJUDGE.judge = "myJudge";
		final List<MunicipalityJudge> MUNICIPALITYJUDGES = Arrays.asList(new MunicipalityJudge[]{MUNIJUDGE});
		
		final int COURTID = 1234;
		when(jdbcTemplate.query(Matchers.anyString(),Matchers.anyMap(), Matchers.<RowMapper<MunicipalityJudge>>any())).thenReturn(MUNICIPALITYJUDGES);

		List<MunicipalityJudge> municipalityJudges = municipalityJudgeDAO.getByCourtId(COURTID);
		assertThat(municipalityJudges.get(0).judge, is("myJudge"));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void returnsMunicipalityJudgeFromJudgeId(){
		final MunicipalityJudge MUNICIPALITYJUDGE = new MunicipalityJudge();
		MUNICIPALITYJUDGE.judge = "myJudge";
		final int JUDGEID = 1234;
		when(jdbcTemplate.queryForObject(Matchers.anyString(), Matchers.anyMap(), Matchers.<RowMapper<MunicipalityJudge>>any()))
        .thenReturn(MUNICIPALITYJUDGE);

		MunicipalityJudge municipalityJudge = municipalityJudgeDAO.getByMunicipalityJudgeId(JUDGEID);
		
		assertThat(municipalityJudge.judge, is("myJudge"));
	}
	
}
