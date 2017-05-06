package svc.data.citations.filters;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ListIterator;

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

	public List<Citation> RemoveCitationsWithExpiredDates(List<Citation> citations){
		ListIterator<Citation> iter = citations.listIterator();
		while(iter.hasNext()){
			Citation citation = iter.next();
			boolean hasWarrant = false;
			ListIterator<Violation> violIter = citation.violations.listIterator();
			while(violIter.hasNext()){
				Violation violation = violIter.next();
				if (violation.warrant_status){
					hasWarrant = true;
					break;
				}
			}
			if (!hasWarrant){
				Court court = courtManager.getCourtById(citation.court_id.getValue());
				if (court.citation_expires_after_days >= 0){
					LocalDateTime today = LocalDateTime.now();
					if (citation.court_dateTime.isBefore(today.minusDays(court.citation_expires_after_days))){
						iter.remove();
					}
				}
			}
		}
		return citations;
	}
}
