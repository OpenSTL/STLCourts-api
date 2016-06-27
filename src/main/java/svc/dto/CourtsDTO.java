package svc.dto;

import java.util.List;

import svc.models.Court;

public class CourtsDTO  {
	public List<Court> courts;
	
	public CourtsDTO(List<Court> courts) {
		this.courts = courts;
	}
}
