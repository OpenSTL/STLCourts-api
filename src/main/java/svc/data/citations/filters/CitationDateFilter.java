package svc.data.citations.filters;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ListIterator;

import svc.data.citations.datasources.CITATION_DATASOURCE;
import svc.models.Citation;

public class CitationDateFilter {

	public static List<Citation> FilterDates(List<Citation> citations){
		ListIterator<Citation> iter = citations.listIterator();
		while(iter.hasNext()){
			Citation citation = iter.next();
			if (citation.citation_datasource != CITATION_DATASOURCE.MOCK){
				LocalDateTime today = LocalDateTime.now();
				if (citation.court_dateTime.isBefore(today)){
					iter.remove();
				}
			}
		}
		return citations;
	}
}
