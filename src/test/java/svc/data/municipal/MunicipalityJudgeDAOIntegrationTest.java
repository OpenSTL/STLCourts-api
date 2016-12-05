package svc.data.municipal;

import static org.junit.Assert.*;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.*;
import org.springframework.test.context.web.WebAppConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;

import svc.Application;
import svc.models.MunicipalityJudge;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class MunicipalityJudgeDAOIntegrationTest {
	
	@Autowired
    private MunicipalityJudgeDAO dao;

	@Test
	public void GetJudgesByCourtIdSuccessful() {
		List<MunicipalityJudge> municipalityJudges = dao.getByCourtId(1);

		assertThat(municipalityJudges, is(notNullValue()));
		assertThat(municipalityJudges.get(0).judge, is("Virginia Nye"));
	}
	
	@Test
	public void GetJudgesByCourtIdUnSuccessful() {
		List<MunicipalityJudge> municipalityJudges = dao.getByCourtId(0);
		assertThat(municipalityJudges.isEmpty(),is(true));
		//assertThat(municipalityJudges, is(nullValue()));
	}
	
	@Test
	public void GetJudgeByJudgeIdSuccessful() {
		MunicipalityJudge municipalityJudge = dao.getByMunicipalityJudgeId(1);

		assertThat(municipalityJudge, is(notNullValue()));
		assertThat(municipalityJudge.judge, is("Virginia Nye"));
	}
	
	@Test
	public void GetJudgeByJudgeIdUnSuccessful() {
		MunicipalityJudge municipalityJudge = dao.getByMunicipalityJudgeId(-1);

		assertThat(municipalityJudge, is(nullValue()));
	}

}
