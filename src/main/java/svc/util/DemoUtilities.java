package svc.util;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import svc.models.Citation;
import svc.models.Court;
import svc.models.VIOLATION_STATUS;
import svc.models.Violation;
import org.hashids.Hashids;
import svc.types.HashableEntity;

public class DemoUtilities {
	
	private Hashids demoRandomizer = new Hashids(DatabaseUtilities.convertLocalDateTimeToDatabaseDateString(DatabaseUtilities.getCurrentDateTime()),5);
	
	public List<Citation> generateRandomCitations(){
	
		Citation citation0 = new Citation();
		citation0.citation_number = "STLC"+demoRandomizer.encode(1L);
		citation0.citation_date = DatabaseUtilities.getCurrentDate().minusDays(10);
		citation0.first_name = "Ina";
		citation0.last_name = "Maxwell";
		citation0.date_of_birth = DatabaseUtilities.convertUSStringDateToLD("05/06/1991");
		citation0.defendant_address = "9 Orin Parkway";
		citation0.defendant_city = "NORWOOD COURT";
		citation0.defendant_state = "MO";
		citation0.drivers_license_number = "STLDL"+demoRandomizer.encode(11L);
		citation0.court_dateTime = DatabaseUtilities.getCurrentDateTime().plusDays(1);
		citation0.court_id = new HashableEntity<Court>(Court.class,2L);
		
		Citation citation1 = new Citation();
		citation1.citation_number = "STLC"+demoRandomizer.encode(2L);
		citation1.citation_date = DatabaseUtilities.getCurrentDate().minusDays(7);
		citation1.first_name = "Teresa";
		citation1.last_name = "Witherspoon";
		citation1.date_of_birth = DatabaseUtilities.convertUSStringDateToLD("02/01/1965");
		citation1.defendant_address = "608 Oak Park";
		citation1.defendant_city = "PASADENA HILLS";
		citation1.defendant_state = "MO";
		citation1.drivers_license_number = "STLDL"+demoRandomizer.encode(22L);
		citation1.court_dateTime = DatabaseUtilities.getCurrentDateTime().plusDays(7);
		citation1.court_id = new HashableEntity<Court>(Court.class,37L);
		
		Citation citation2 = new Citation();
		citation2.citation_number = "STLC"+demoRandomizer.encode(3L);
		citation2.citation_date = DatabaseUtilities.getCurrentDate().minusDays(5);
		citation2.first_name = "George";
		citation2.last_name = "Hamer";
		citation2.date_of_birth = DatabaseUtilities.convertUSStringDateToLD("03/18/1994");
		citation2.defendant_address = "1 Clemons Terrace";
		citation2.defendant_city = "ST. ANN";
		citation2.defendant_state = "MO";
		citation2.drivers_license_number = "STLDL"+demoRandomizer.encode(33L);
		citation2.court_dateTime = DatabaseUtilities.getCurrentDateTime().plusDays(5);
		citation2.court_id = new HashableEntity<Court>(Court.class,79L);
		
		Citation citation3 = new Citation();
		citation3.citation_number = "STLC"+demoRandomizer.encode(4L);
		citation3.citation_date = DatabaseUtilities.getCurrentDate().minusDays(15);
		citation3.first_name = "Jacob";
		citation3.last_name = "Cherry";
		citation3.date_of_birth = DatabaseUtilities.convertUSStringDateToLD("08/28/1961");
		citation3.defendant_address = "9 Dryden Street";
		citation3.defendant_city = "BRIDGETON";
		citation3.defendant_state = "MO";
		citation3.drivers_license_number = "STLDL"+demoRandomizer.encode(44L);
		citation3.court_dateTime = DatabaseUtilities.getCurrentDateTime().plusDays(14);
		citation3.court_id = new HashableEntity<Court>(Court.class,1L);
		
		Citation citation4 = new Citation();
		citation4.citation_number = "STLC"+demoRandomizer.encode(5L);
		citation4.citation_date = DatabaseUtilities.getCurrentDate().minusDays(6);
		citation4.first_name = "Ella";
		citation4.last_name = "Olsen";
		citation4.date_of_birth = DatabaseUtilities.convertUSStringDateToLD("10/11/1958");
		citation4.defendant_address = "27848 Katie Plaza";
		citation4.defendant_city = "EUREKA";
		citation4.defendant_state = "MO";
		citation4.drivers_license_number = "STLDL"+demoRandomizer.encode(55L);
		citation4.court_dateTime = DatabaseUtilities.getCurrentDateTime().plusDays(12);
		citation4.court_id = new HashableEntity<Court>(Court.class,6L);
		
		Citation citation5 = new Citation();
		citation5.citation_number = "STLC"+demoRandomizer.encode(6L);
		citation5.citation_date = DatabaseUtilities.getCurrentDate().minusDays(20);
		citation5.first_name = "Ella";
		citation5.last_name = "Olsen";
		citation5.date_of_birth = DatabaseUtilities.convertUSStringDateToLD("10/11/1958");
		citation5.defendant_address = "27848 Katie Plaza";
		citation5.defendant_city = "EUREKA";
		citation5.defendant_state = "MO";
		citation5.drivers_license_number = "STLDL"+demoRandomizer.encode(55L);
		citation5.court_dateTime = DatabaseUtilities.getCurrentDateTime().plusDays(4);
		citation5.court_id = new HashableEntity<Court>(Court.class,23L);
		
		List <Citation> citations = Arrays.asList(citation0,citation1,citation2,citation3,citation4, citation5);
		
		return citations;
	}
	
	public List<Violation> generateRandomViolations(){
		Violation violation0 = new Violation();
		violation0.citation_number = "STLC"+demoRandomizer.encode(1L);
		violation0.violation_number = "STLC"+demoRandomizer.encode(1L) +"-01";
		violation0.violation_description = "Improper Passing";
		violation0.warrant_status = false;
		violation0.warrant_number = "";
		violation0.status = VIOLATION_STATUS.CONT_FOR_PAYMENT;
		violation0.fine_amount = new BigDecimal("139.04");
		violation0.court_cost = new BigDecimal("24.50");
		
		Violation violation1 = new Violation();
		violation1.citation_number = "STLC"+demoRandomizer.encode(1L);
		violation1.violation_number = "STLC"+demoRandomizer.encode(1L) +"-02";
		violation1.violation_description = "Improper Passing";
		violation1.warrant_status = false;
		violation1.warrant_number = "";
		violation1.status = VIOLATION_STATUS.CONT_FOR_PAYMENT;
		violation1.fine_amount = new BigDecimal("80.45");
		violation1.court_cost = new BigDecimal("24.50");
		
		Violation violation2 = new Violation();
		violation2.citation_number = "STLC"+demoRandomizer.encode(2L);
		violation2.violation_number = "STLC"+demoRandomizer.encode(2L) +"-01";
		violation2.violation_description = "Prohibited U-Turn";
		violation2.warrant_status = false;
		violation2.warrant_number = "";
		violation2.status = VIOLATION_STATUS.CONT_FOR_PAYMENT;
		violation2.fine_amount = new BigDecimal("77.44");
		violation2.court_cost = new BigDecimal("24.50");
		
		Violation violation3 = new Violation();
		violation3.citation_number = "STLC"+demoRandomizer.encode(3L);
		violation3.violation_number = "STLC"+demoRandomizer.encode(3L) +"-01";
		violation3.violation_description = "Parking in Fire Zone";
		violation3.warrant_status = false;
		violation3.warrant_number = "";
		violation3.status = VIOLATION_STATUS.CONT_FOR_PAYMENT;
		violation3.fine_amount = new BigDecimal("84.32");
		violation3.court_cost = new BigDecimal("24.50");
		
		Violation violation4 = new Violation();
		violation4.citation_number = "STLC"+demoRandomizer.encode(3L);
		violation4.violation_number = "STLC"+demoRandomizer.encode(3L) +"-02";
		violation4.violation_description = "No Drivers License";
		violation4.warrant_status = false;
		violation4.warrant_number = "";
		violation4.status = VIOLATION_STATUS.CONT_FOR_PAYMENT;
		violation4.fine_amount = new BigDecimal("58.88");
		violation4.court_cost = new BigDecimal("24.50");
		
		Violation violation5 = new Violation();
		violation5.citation_number = "STLC"+demoRandomizer.encode(4L);
		violation5.violation_number = "STLC"+demoRandomizer.encode(4L) +"-01";
		violation5.violation_description = "No Insurance [no compliance]";
		violation5.warrant_status = false;
		violation5.warrant_number = "";
		violation5.status = VIOLATION_STATUS.CONT_FOR_PAYMENT;
		violation5.fine_amount = new BigDecimal("153.89");
		violation5.court_cost = new BigDecimal("24.50");
		
		Violation violation6 = new Violation();
		violation6.citation_number = "STLC"+demoRandomizer.encode(5L);
		violation6.violation_number = "STLC"+demoRandomizer.encode(5L) +"-01";
		violation6.violation_description = "No Insurance [no compliance]";
		violation6.warrant_status = false;
		violation6.warrant_number = "";
		violation6.status = VIOLATION_STATUS.CONT_FOR_PAYMENT;
		violation6.fine_amount = new BigDecimal("153.89");
		violation6.court_cost = new BigDecimal("24.50");
		
		Violation violation7 = new Violation();
		violation7.citation_number = "STLC"+demoRandomizer.encode(5L);
		violation7.violation_number = "STLC"+demoRandomizer.encode(5L) +"-02";
		violation7.violation_description = "Failure to Yield";
		violation7.warrant_status = false;
		violation7.warrant_number = "";
		violation7.status = VIOLATION_STATUS.CONT_FOR_PAYMENT;
		violation7.fine_amount = new BigDecimal("153.89");
		violation7.court_cost = new BigDecimal("24.50");
		
		Violation violation8 = new Violation();
		violation8.citation_number = "STLC"+demoRandomizer.encode(6L);
		violation8.violation_number = "STLC"+demoRandomizer.encode(6L) +"-01";
		violation8.violation_description = "Parking in Fire Zone";
		violation8.warrant_status = false;
		violation8.warrant_number = "";
		violation8.status = VIOLATION_STATUS.CONT_FOR_PAYMENT;
		violation8.fine_amount = new BigDecimal("155.07");
		violation8.court_cost = new BigDecimal("24.50");
		
		List <Violation> violations = Arrays.asList(violation0,violation1,violation2,violation3,violation4,violation5,violation6,violation7,violation8);
		return violations;
	}
}
