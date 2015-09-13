package svc.dto;

import java.util.List;

import svc.models.Court;

public class CourtsDTO 
{
	public CourtsDTO(List<Court> models)
	{
		this.courts = models;
	}
	
	public List<Court> courts;
}
