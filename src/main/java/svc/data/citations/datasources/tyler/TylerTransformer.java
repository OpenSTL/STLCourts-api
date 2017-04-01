package svc.data.citations.datasources.tyler;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import svc.data.jdbc.BaseJdbcDao;
import svc.logging.LogSystem;
import svc.models.Citation;
import svc.models.Court;
import svc.models.Violation;
import svc.types.HashableEntity;

@Component
public class TylerTransformer extends BaseJdbcDao {

	private DateTimeFormatter localDateFormatter = DateTimeFormatter.ofPattern("MM/dd/uuuu");

	public List<Citation> citationsFromTylerCitations(List<TylerCitation> tylerCitations) {
		if (tylerCitations != null) {
			return tylerCitations.stream()
					.map(tylerCitation -> citationFromTylerCitation(tylerCitation))
					.collect(Collectors.toList());
		}
		return null;
	}

	private LocalDateTime parseViolationCourtDate(String violationCourtDateString) {
		try {
			return LocalDateTime.parse(violationCourtDateString);
		} catch (DateTimeParseException ex) {
		}
		return null;
	}

	public Citation citationFromTylerCitation(TylerCitation tylerCitation) {

		Citation output = new Citation();
		output.citation_number = tylerCitation.citationNumber;
		output.first_name = tylerCitation.firstName;
		output.last_name = tylerCitation.lastName;
		output.date_of_birth = LocalDate.parse(tylerCitation.dob, localDateFormatter);
		output.drivers_license_number = tylerCitation.driversLicenseNumber;
		output.citation_date = LocalDate.parse(tylerCitation.violationDate, localDateFormatter);

		List<LocalDateTime> violationCourtDates = null;
		violationCourtDates = tylerCitation.violations.stream()
				.map((violation) -> violation.courtDate)
				.distinct()
				.map(this::parseViolationCourtDate)
				.collect(Collectors.toList());
		output.court_dateTime = violationCourtDates.size() > 0 ? violationCourtDates.get(0) : null;

		output.violations = violationsFromTylerCitation(tylerCitation);
		output.court_id = lookupCourtId(getTylerCourtIdentifier(tylerCitation));

		// These could probably be added to the Tyler API
		// citation time?
		// Boolean mandatory_court_apperarnce
		// Boolean can_pay_online
		// public String defendant_address;  - not in XML
		// public String defendant_city; - not in XML
		// public String defendant_state; - not in XML
		// Nice to add to our api DL State.

		// There is no property for muni_id, but there probably should be

		return output;
	}

	public List<Violation> violationsFromTylerCitation(TylerCitation tylerCitation) {
		if (tylerCitation.violations != null) {
			return tylerCitation.violations.stream()
					.map(tylerViolation -> violationFromTylerViolation(tylerCitation, tylerViolation))
					.collect(Collectors.toList());
		}
		return null;
	}

	public Violation violationFromTylerViolation(TylerCitation tylerCitation, TylerViolation tylerViolation) {
		Violation output = new Violation();

		output.citation_number = tylerCitation.citationNumber;
		output.violation_number = tylerViolation.violationNumber;
		output.violation_description = tylerViolation.violationDescription;
		output.warrant_status = tylerViolation.warrantStatus;
		output.warrant_number = tylerViolation.warrantNumber;
		output.fine_amount = BigDecimal.valueOf(tylerViolation.fineAmount);
		output.court_cost = null;

		TylerViolationStatus violationStatus = TylerViolationStatus.fromTylerViolationStatusString(tylerViolation.status);
		output.status = violationStatus != null ? violationStatus.getViolationStatus() : null;

		// These might be available in the Tyler API, but they aren't piped
		// through currently
		// public LocalDate status_date;  - removed from this api
		// public BigDecimal court_cost;

		return output;
	}
	
	private String getTylerCourtIdentifier(TylerCitation tylerCitation){
		String tylerCourtIdentifier = null;
		if (tylerCitation.violations != null && tylerCitation.violations.size() > 0){
			for(TylerViolation tylerViolation: tylerCitation.violations){
				tylerCourtIdentifier = tylerViolation.courtName;
				break;
			}
		}
		
		return tylerCourtIdentifier;
	}
	
	private HashableEntity<Court> lookupCourtId(String tylerCourtIdentifier){
		if (tylerCourtIdentifier != null){
		    try {
	            Map<String, Object> parameterMap = new HashMap<String, Object>();
	            parameterMap.put("tylerCourtIdentifier", tylerCourtIdentifier);
	            String sql = "SELECT court_id FROM tyler_court_mapping WHERE datasource_identifier = :tylerCourtIdentifier";
	            Long courtId = jdbcTemplate.queryForObject(sql, parameterMap, new CourtIdSQLMapper());
	            return new HashableEntity<Court>(Court.class,courtId);
	        } catch (Exception e) {
	            LogSystem.LogDBException(e);
	            return null;
	        }
		}else{
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
