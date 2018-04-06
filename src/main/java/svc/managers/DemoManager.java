package svc.managers;


import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import svc.data.citations.datasources.mock.MockCitationDataSource;
import svc.models.Citation;
import svc.models.DemoCitation;
import svc.models.Violation;
import svc.util.DatabaseUtilities;
import svc.util.DemoUtilities;

@Component
public class DemoManager {
	@Inject
    private MockCitationDataSource mockCitationDataSource;
	
	@Inject
	private ViolationManager violationManager;
	
	public List<DemoCitation> createCitationsAndViolations(){
		List<DemoCitation> demoCitations = new ArrayList<DemoCitation>();
		
		DemoUtilities demoUtilities = new DemoUtilities();
		List<Citation> citations = demoUtilities.generateRandomCitations();
		List<Violation> violations = demoUtilities.generateRandomViolations();
		
		mockCitationDataSource.insertCitations(citations);
		violationManager.insertViolations(violations);
		
		for(int citationCount = 0; citationCount < citations.size(); citationCount++){
			Citation citation = citations.get(citationCount);
			int numberOfViolations = 0;
			for(int violationCount = 0; violationCount < violations.size(); violationCount++){
				if (violations.get(violationCount).citation_number.equals(citation.citation_number)){
					numberOfViolations++;
				}
			}
			DemoCitation demoCitation = new DemoCitation();
			demoCitation.lastName = citation.last_name;
			demoCitation.citationNumber = citation.citation_number;
			demoCitation.dob = citation.date_of_birth;
			demoCitation.driversLicenseNumber = citation.drivers_license_number;
			demoCitation.driversLicenseState = citation.drivers_license_state;
			Period daysTillCourt = DatabaseUtilities.getCurrentDate().until(citation.court_dateTime.toLocalDate());
			demoCitation.daysTillCourt = daysTillCourt.getDays();
			demoCitation.numberOfViolations = numberOfViolations;
			
			demoCitations.add(demoCitation);
		}
		
		Timer timer = new Timer();
		timer.schedule(new TimerTask(){

			@Override
			public void run() {
				mockCitationDataSource.removeCitations(citations);
				violationManager.removeViolations(violations);
			}
			
		}, 60*60*1000);
		
		return demoCitations;
	}
}
