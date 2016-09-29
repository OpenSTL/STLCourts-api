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
	
	public Court GetCourtById(Long courtId) {
		return courtDAO.getByCourtId(courtId);
	}

	public List<Court> GetAllCourts() {
		return courtDAO.getAllCourts();
	}
}
