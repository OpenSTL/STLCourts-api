package svc.managers;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import svc.data.CourtDAO;
import svc.models.Court;

@Component
public class CourtManager
{
	@Inject
	private CourtDAO _courtDAO;
	
	public CourtManager()
	{
	}
	
	public Court GetCourtById(int courtId)
	{
		return _courtDAO.getByCourtId(courtId);
	}

}
