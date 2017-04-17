package svc.managers;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import svc.data.municipal.MunicipalityDAO;
import svc.models.Municipality;

@Component
public class MunicipalityManager {
	@Inject
	private MunicipalityDAO municipalityDAO;
	
	public List<Municipality> GetMunicipalitiesByCourtId(Long courtId){
		return municipalityDAO.getByCourtId(courtId);
	}
	
	public Municipality GetMunicipalityById(Long municipalityId){
		return municipalityDAO.getByMunicipalityId(municipalityId);
	}

	public List<Municipality> GetAllMunicipalities(Boolean supported) {
		return municipalityDAO.getAllMunicipalities(supported);
	}
}
