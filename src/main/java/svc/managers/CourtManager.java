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
		
	public Court getCourtById(Long courtId){
		return courtDAO.getCourtById(courtId);
	}

	public List<Court> getCourtsByMunicipalityId(Long municipalityId) {
		return courtDAO.getCourtsByMunicipalityId(municipalityId);
	}

	public List<Court> getAllCourts() {
		return courtDAO.getAllCourts();
	}
	
}
