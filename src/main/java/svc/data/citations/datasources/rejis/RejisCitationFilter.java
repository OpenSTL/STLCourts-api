package svc.data.citations.datasources.rejis;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import svc.data.citations.datasources.rejis.models.RejisPartialCitation;

@Component
public class RejisCitationFilter {

	public List<RejisPartialCitation> Filter(List<RejisPartialCitation> partialCitations){
		return partialCitations.stream()
				 .filter(c -> statusFilter(c))
				 .collect(Collectors.toList());
	}

	private boolean statusFilter(RejisPartialCitation partialCitation){
		boolean keepCitation = false;

		if (!partialCitation.caseStatus.equals("C")){
			keepCitation = true;
		}
		
		return keepCitation;
	}
	
}
