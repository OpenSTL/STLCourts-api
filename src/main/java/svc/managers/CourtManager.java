package svc.managers;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import svc.data.municipal.CourtDAO;
import svc.models.Court;

@Component
public class CourtManager {
	@Inject
	private CourtDAO courtDAO;
	@Inject
	private MunicipalityJudgeManager municipalityJudgeManager;
	
	public Court GetCourtById(Long courtId){
		Court court = courtDAO.getByCourtId(courtId); 
		if (court != null){
			court.municipality_judges = municipalityJudgeManager.GetAllMunicipalityJudgesFromCourtId(court.id);
		}
		return court;
	}

	public List<Court> GetAllCourts() {
		List<Court> courts = courtDAO.getAllCourts();
		return PopulateMunicipalityJudges(courts);
	}
	
	private List<Court> PopulateMunicipalityJudges(List<Court> courts) {
		if (courts == null) {
			return null;
		}
		
		for (Court court:courts) {
			court.municipality_judges = municipalityJudgeManager.GetAllMunicipalityJudgesFromCourtId(court.id);
		}
		return courts;
	}
}
