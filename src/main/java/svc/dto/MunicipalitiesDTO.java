package svc.dto;

import java.util.List;

import svc.models.Municipality;

public class MunicipalitiesDTO  {
	public List<Municipality> municipalities;
	
	public MunicipalitiesDTO(List<Municipality> municipalities) {
		this.municipalities = municipalities;
	}
}
