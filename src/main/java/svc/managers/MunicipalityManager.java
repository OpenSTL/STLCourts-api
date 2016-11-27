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
	
	public Municipality GetMunicipalityById(Long municipalityId){
		return municipalityDAO.getByMunicipalityId(municipalityId);
	}

	public List<Municipality> GetAllMunicipalities() {
		return municipalityDAO.getAllMunicipalities();
	}
}
