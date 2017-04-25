package svc.data.citations.filters;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ListIterator;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import svc.managers.CourtManager;
import svc.models.Citation;
import svc.models.Court;

@Component
public class FilterCitations {
	@Inject
	CourtManager courtManager;

	public List<Citation> FilterDates(List<Citation> citations){
		ListIterator<Citation> iter = citations.listIterator();
		while(iter.hasNext()){
			Citation citation = iter.next();
			Court court = courtManager.getCourtById(citation.court_id.getValue());
			if (court.citation_expires_after_days >= 0){
				LocalDateTime today = LocalDateTime.now();
				if (citation.court_dateTime.isBefore(today.minusDays(court.citation_expires_after_days))){
					iter.remove();
				}
			}
			
		}
		return citations;
	}
}
