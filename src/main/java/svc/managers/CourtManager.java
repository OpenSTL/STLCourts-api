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
		
	public Court GetCourtById(Long courtId){
		Court court = courtDAO.getByCourtId(courtId); 
		
		return court;
	}

	public List<Court> GetAllCourts() {
		List<Court> courts = courtDAO.getAllCourts();
		return courts;
	}
	
}
