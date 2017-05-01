package svc.data.citations;

import svc.models.Citation;

import java.time.LocalDate;
import java.util.List;

public interface CitationDataSource {
    List<Citation> getByCitationNumberAndDOB(String citationNumber, LocalDate dob);
    List<Citation> getByLicenseAndDOB(String driversLicenseNumber, String driversLicenseState, LocalDate dob);
    List<Citation> getByNameAndMunicipalitiesAndDOB(String lastName, List<Long> municipalities, LocalDate dob);
}
