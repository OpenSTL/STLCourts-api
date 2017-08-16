package svc.data.citations.datasources.rejis.transformers;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import svc.data.citations.datasources.rejis.models.RejisFullCitation;
import svc.data.citations.datasources.rejis.models.RejisPartialCitation;
import svc.logging.LogSystem;
import svc.models.Violation;

@Component
public class RejisViolationTransformer {

	public List<Violation> fromRejisFullCitation(RejisFullCitation rejisFullCitation, RejisPartialCitation rejisPartialCitation) {
		Violation genericViolation = new Violation();
		genericViolation.citation_number = rejisFullCitation.TktNum;
		genericViolation.violation_number = rejisFullCitation.TktNum;
		genericViolation.violation_description = rejisFullCitation.ChrgDesc;
		genericViolation.warrant_status = rejisFullCitation.CaseStatus.equals("W");
		genericViolation.warrant_number = "";
		try{
			genericViolation.fine_amount = BigDecimal.valueOf(rejisFullCitation.BalDue);
		}catch(Exception e){
			LogSystem.LogEvent("Error converting Rejis BalDue to BigDecimal");
			genericViolation.fine_amount = BigDecimal.valueOf(0L);
		}
		genericViolation.can_pay_online = rejisPartialCitation.ShowIpaycourt;
		genericViolation.court_cost = null;

		//note violation status doesn't get used so not setting it here
		return Arrays.asList(genericViolation);
	}

}
