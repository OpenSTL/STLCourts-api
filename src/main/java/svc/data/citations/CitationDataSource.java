package svc.data.citations;

import svc.models.Citation;

import java.util.Date;
import java.util.List;

public interface CitationDataSource {
    List<Citation> getByCitationNumberAndDOB(String citationNumber, Date dob);
    List<Citation> getByLicenseAndDOB(String driversLicenseNumber, Date dob);
    List<Citation> getByNameAndMunicipalitiesAndDOB(String lastName, List<Long> municipalities, Date dob);
}
