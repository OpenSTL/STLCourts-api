package svc.models;

import svc.types.HashableEntity;

public class Judge 
{
	public HashableEntity<Judge> id;
	public String judge;
	public HashableEntity<Court> court_id;
}
