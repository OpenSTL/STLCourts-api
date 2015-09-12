package svc.dto;

import java.util.List;

import svc.models.Citation;

public class CitationsDTO 
{
	public CitationsDTO(List<Citation> models)
	{
		this.citations = models;
	}
	
	public List<Citation> citations;
}
