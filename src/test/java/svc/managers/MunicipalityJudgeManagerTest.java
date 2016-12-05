package svc.managers;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.*;

import svc.data.municipal.MunicipalityJudgeDAO;
import svc.dto.CourtsDTO;
import svc.managers.CourtManager;
import svc.models.Court;
import svc.models.MunicipalityJudge;

@RunWith(MockitoJUnitRunner.class)
public class MunicipalityJudgeManagerTest {

	@InjectMocks
	MunicipalityJudgeManager manager;
	
	@Mock
	MunicipalityJudgeDAO municipalityJudgeDAOMock;
	
	@Test
	public void returnsMunicipalityJudgeFromJudgeId(){
		final MunicipalityJudge MUNICIPALITYJUDGE = new MunicipalityJudge();
		final int MUNICIPALITYJUDGEID = 1;
		when(municipalityJudgeDAOMock.getByMunicipalityJudgeId(MUNICIPALITYJUDGEID)).thenReturn(MUNICIPALITYJUDGE);
		assertThat(manager.GetMunicipalityJudgeById(MUNICIPALITYJUDGEID),equalTo(MUNICIPALITYJUDGE));
	}
	
	@Test
	public void returnsAllMunicipalityJudgesFromCourtId(){
		final List<MunicipalityJudge> MUNICIPALITYJUDGES = Arrays.asList(new MunicipalityJudge[]{new MunicipalityJudge()});
		final int COURTID = 1;
		when(municipalityJudgeDAOMock.getByCourtId(COURTID)).thenReturn(MUNICIPALITYJUDGES);
		assertThat(manager.GetAllMunicipalityJudgesFromCourtId(COURTID),equalTo(MUNICIPALITYJUDGES));
	}
}
