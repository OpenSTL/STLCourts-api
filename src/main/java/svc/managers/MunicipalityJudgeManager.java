package svc.managers;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import svc.data.municipal.MunicipalityJudgeDAO;
import svc.models.MunicipalityJudge;

@Component
public class MunicipalityJudgeManager {
	@Inject
	private MunicipalityJudgeDAO municipalityJudgeDAO;
	
	public MunicipalityJudge GetMunicipalityJudgeById(int municipalityJudgeId){
		return municipalityJudgeDAO.getByMunicipalityJudgeId(municipalityJudgeId);
	}

	public List<MunicipalityJudge> GetAllMunicipalityJudgesFromCourtId(int courtId) {
		return municipalityJudgeDAO.getByCourtId(courtId);
	}
}
