package svc.data.citations.datasources.tyler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import svc.data.citations.CitationDataSource;
import svc.models.Citation;

import java.util.Date;
import java.util.List;

@Repository
public class TylerCitationDataSource implements CitationDataSource {
    @Autowired
    private TylerConfiguration tylerConfiguration;

    @Override
    public Citation getByCitationNumberAndDOB(String citationNumber, Date dob) {
        return null; //TODO
    }

    @Override
    public List<Citation> getByLicenseAndDOB(String driversLicenseNumber, Date dob) {
        return null; //TODO
    }

    @Override
    public List<Citation> getByNameAndMunicipalitiesAndDOB(String lastName, List<Long> municipalities, Date dob) {
        return null; //TODO
    }
}
