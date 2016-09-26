package svc.models;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class OpportunityNeed {

	public Long id;
	public Long opportunityId;
	
	public String description;
	
	public Timestamp startTime;
	public Timestamp endTime;
	
	public BigDecimal violationFineLimit;
	
	public int desiredCount;
	
	public List<Integer> opportunityPairingIds;
}
