package svc.data.citations.filters;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import svc.managers.CourtManager;
import svc.models.Citation;
import svc.models.Court;
import svc.models.Violation;

@Component
public class CitationFilter {
	@Inject
	CourtManager courtManager;

	public List<Citation> Filter(List<Citation> citations, String lastName){
		return citations.stream()
				 .filter(c -> courtDateFilter(c))
				 .filter(c -> lastNameFilter(c,lastName))
				 .collect(Collectors.toList());
	}

	private boolean courtDateFilter(Citation citation){
		boolean keepCitation = false;

		ListIterator<Violation> violIter = citation.violations.listIterator();
		while(violIter.hasNext()){
			Violation violation = violIter.next();
			if (violation.warrant_status){
				keepCitation = true;
				break;
			}
		}

		if (!keepCitation){
			Court court = courtManager.getCourtById(citation.court_id.getValue());
			if (court.citation_expires_after_days >= 0){
				LocalDateTime today = LocalDateTime.now();
				if (citation.court_dateTime.isBefore(today.minusDays(court.citation_expires_after_days))){
					keepCitation = false;
				}else{ //citation has not expired yet
					keepCitation = true;
				}
			}else{ //court field indicates citation never expires
				keepCitation = true;
			}
		}

		return keepCitation;
	}
	
	
	private boolean lastNameFilter(Citation citation, String lastName){
		boolean keepCitation = true;
		if (lastName != null  && !lastName.isEmpty()){
			if (!lastName.equalsIgnoreCase(citation.last_name)){
				keepCitation = false;
			}
		}
		
		return keepCitation;
	}
}
