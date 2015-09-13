package svc.models;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class OpportunityNeed {

	public int id;
	public int opportunityId;
	
	public Timestamp startTime;
	public Timestamp endTime;
	
	public BigDecimal violationFineLimit;
	
	public int desired_count;
	
	public List<Integer> opportunityPairingIds;
}
