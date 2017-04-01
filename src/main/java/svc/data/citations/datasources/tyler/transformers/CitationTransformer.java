package svc.data.citations.datasources.tyler.transformers;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import svc.data.citations.datasources.tyler.models.TylerCitation;
import svc.data.jdbc.BaseJdbcDao;
import svc.logging.LogSystem;
import svc.models.Citation;
import svc.models.Court;
import svc.types.HashableEntity;

@Component
public class CitationTransformer extends BaseJdbcDao {

	@Autowired
	ViolationTransformer violationTransformer;

	private DateTimeFormatter localDateFormatter = DateTimeFormatter.ofPattern("MM/dd/uuuu");

	public List<Citation> fromTylerCitations(List<TylerCitation> tylerCitations) {
		if (tylerCitations != null) {
			return tylerCitations.stream()
					.map(tylerCitation -> fromTylerCitation(tylerCitation))
					.collect(Collectors.toList());
		}
		return null;
	}

	private LocalDateTime parseViolationCourtDate(String violationCourtDateString) {

		if (violationCourtDateString == null) {
			return null;
		}

		LocalDateTime violationCourtDate = null;
		try {
			violationCourtDate = LocalDateTime.parse(violationCourtDateString);
		} catch (DateTimeParseException ex) {
			LogSystem.LogEvent("Failed to parse tyler violation court date: " + ex.getLocalizedMessage());
		}
		return violationCourtDate;
	}

	public Citation fromTylerCitation(TylerCitation tylerCitation) {
		if (tylerCitation == null) {
			return null;
		}

		Citation genericCitation = new Citation();
		genericCitation.citation_number = tylerCitation.citationNumber;
		genericCitation.first_name = tylerCitation.firstName;
		genericCitation.last_name = tylerCitation.lastName;

		if (tylerCitation.dob == null) {
			LogSystem.LogEvent("Received tyler citation with no DOB.");
		} else {
			genericCitation.date_of_birth = LocalDate.parse(tylerCitation.dob, localDateFormatter);
		}
		genericCitation.drivers_license_number = tylerCitation.driversLicenseNumber;

		if (tylerCitation.violationDate == null) {
			LogSystem.LogEvent("Received tyler citation with no violation date.");
		} else {
			genericCitation.citation_date = LocalDate.parse(tylerCitation.violationDate, localDateFormatter);
		}

		if (tylerCitation.violations == null) {
			LogSystem.LogEvent("No violations received with Tyler citation. Skipping fields that require them.");
		} else {
			List<LocalDateTime> violationCourtDates = null;
			violationCourtDates = tylerCitation.violations.stream()
					.map((violation) -> violation.courtDate)
					.distinct()
					.map(this::parseViolationCourtDate)
					.collect(Collectors.toList());
			genericCitation.court_dateTime = violationCourtDates.size() > 0 ? violationCourtDates.get(0) : null;
			genericCitation.violations = violationTransformer.fromTylerCitation(tylerCitation);
			genericCitation.court_id = lookupCourtId(getTylerCourtIdentifier(tylerCitation));
		}

		// These could probably be added to the Tyler API
		// citation time?
		// Boolean mandatory_court_apperarnce
		// Boolean can_pay_online

		// public String defendant_address; - not in Tyler API
		// public String defendant_city; - not in Tyler API
		// public String defendant_state; - not in Tyler API

		// There is no property for muni_id, but there probably should be

		return genericCitation;
	}

	private String getTylerCourtIdentifier(TylerCitation tylerCitation) {

		List<String> tylerCourtIdentifiers = null;
		tylerCourtIdentifiers = tylerCitation.violations.stream()
				.map((violation) -> violation.courtName)
				.distinct()
				.collect(Collectors.toList());

		if (tylerCourtIdentifiers.isEmpty()) {
			LogSystem.LogEvent("No court name provided on tyler violations");
			return null;
		} else {
			if (tylerCourtIdentifiers.size() != 1) {
				LogSystem.LogEvent("Multiple court names provided on tyler violations. Defaulting to first value.");
			}
			return tylerCourtIdentifiers.get(0);
		}
	}

	private HashableEntity<Court> lookupCourtId(String tylerCourtIdentifier) {
		if (tylerCourtIdentifier != null) {
			try {
				Map<String, Object> parameterMap = new HashMap<String, Object>();
				parameterMap.put("tylerCourtIdentifier", tylerCourtIdentifier);
				String sql = "SELECT court_id FROM tyler_court_mapping WHERE datasource_identifier = :tylerCourtIdentifier";
				Long courtId = jdbcTemplate.queryForObject(sql, parameterMap, new CourtIdSQLMapper());
				return new HashableEntity<Court>(Court.class, courtId);
			} catch (Exception e) {
				LogSystem.LogDBException(e);
				return null;
			}
		} else {
			return null;
		}
	}

	private class CourtIdSQLMapper implements RowMapper<Long> {
		public Long mapRow(ResultSet rs, int i) {
			Long courtId;
			try {
				courtId = Long.parseLong(rs.getString("court_id"));
			} catch (Exception e) {
				LogSystem.LogDBException(e);
				return null;
			}

			return courtId;
		}
	}
}
