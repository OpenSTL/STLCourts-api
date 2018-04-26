package svc.data.transformer;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import svc.managers.CourtManager;
import svc.models.Court;
import svc.types.HashableEntity;

@Component
public class CitationDateTimeTransformer {
	@Autowired
	CourtManager courtManager;
	
	public ZonedDateTime transformLocalDateTime(LocalDateTime localDateTime, HashableEntity<Court> courtId){
		if (localDateTime == null) {
			return null;
		} else {
			Court court = courtManager.getCourtById(courtId.getValue());
			return ZonedDateTime.of(localDateTime, ZoneId.of(court.zone_id));
		}
	}
}
