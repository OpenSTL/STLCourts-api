package svc.data.citations;

import com.google.common.collect.Lists;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Component;
import rx.Observable;
import svc.data.jdbc.BaseJdbcDao;
import svc.logging.LogSystem;
import svc.models.Citation;
import svc.util.DatabaseUtilities;

import javax.inject.Inject;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//NOTE: If we switch to groovy, we can greatly reduce code here since we can pass the function as an argument to a method
@Component
public class CitationDAO extends BaseJdbcDao {
	@Inject
    private CitationDataSourceFactory citationDataSourceFactory;

	public List<Citation> getByCitationNumberAndDOB(String citationNumber, LocalDate dob) {
	    List<CitationDataSource> sources = citationDataSourceFactory.getAllCitationDataSources();

        List<Observable<Citation>> citationSearches = Lists.newArrayList();
		for(CitationDataSource source : sources) {
            citationSearches.add(Observable.from(source.getByCitationNumberAndDOB(citationNumber, dob)).onExceptionResumeNext(Observable.just(null)));
		}

		return Observable.merge(citationSearches).onExceptionResumeNext(Observable.just(null)).toList().toBlocking().first();
	}

	public List<Citation> getByLicenseAndDOB(String driversLicenseNumber, LocalDate dob) {
        List<CitationDataSource> sources = citationDataSourceFactory.getAllCitationDataSources();

        List<Observable<Citation>> citationSearches = Lists.newArrayList();
        for(CitationDataSource source : sources) {
            citationSearches.add(Observable.from(source.getByLicenseAndDOB(driversLicenseNumber, dob)));
        }

        return Observable.merge(citationSearches).onExceptionResumeNext(Observable.just(null)).toList().toBlocking().first();
	}
	
	public List<Citation> getByNameAndMunicipalitiesAndDOB(String lastName, List<Long> municipalities, LocalDate dob) {
        List<CitationDataSource> sources = citationDataSourceFactory.getCitationDataSourcesForMunicipalities(municipalities);

        List<Observable<Citation>> citationSearches = Lists.newArrayList();
        for(CitationDataSource source : sources) {
            citationSearches.add(Observable.from(source.getByNameAndMunicipalitiesAndDOB(lastName, municipalities, dob)));
        }

        return Observable.merge(citationSearches).onExceptionResumeNext(Observable.just(null)).toList().toBlocking().first();
	}
	
	public boolean insertDemoCitations(List<Citation> citations){
 		List<String> newCitationNumbers = new ArrayList<String>();
 		
 		try{
 			for(int i = 0; i < citations.size(); i++){
 				Citation c = citations.get(i);
 				String sql = "INSERT INTO citations (citation_number,citation_date,first_name,last_name,date_of_birth,defendant_address,defendant_city,defendant_state,drivers_license_number,court_date,court_location,court_address,court_id) VALUES ('"+c.citation_number+"','"+DatabaseUtilities.convertLocalDateToDatabaseDateString(c.citation_date)+"','"+c.first_name+"','"+c.last_name+"','"+DatabaseUtilities.convertLocalDateToDatabaseDateString(c.date_of_birth)+"','"+c.defendant_address+"','"+c.defendant_city+"','"+c.defendant_state+"','"+c.drivers_license_number+"','"+DatabaseUtilities.convertLocalDateTimeToDatabaseDateString(c.court_dateTime)+"','"+c.court_location+"','"+c.court_address+"',"+c.court_id.getValue()+")";
 				jdbcTemplate.execute(sql, new PreparedStatementCallback<Boolean>(){
					@Override
					public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
						return ps.execute();
					}
 				});
 				newCitationNumbers.add(c.citation_number);
 			}
 		}catch(Exception e){
 			LogSystem.LogDBException(e);
 			return false;
 		}
 		return true;
 	}
		 	
 	public boolean removeDemoCitations(List<Citation> citations){
 		try{
 			for(int i = 0; i < citations.size(); i++){
 				Citation c = citations.get(i);
 				String sql = "DELETE FROM citations WHERE citation_number = '"+c.citation_number+"'";
 				jdbcTemplate.execute(sql, new PreparedStatementCallback<Boolean>(){
 					@Override
					public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
						return ps.execute();
					}
 				});
 			}
 		}catch(Exception e){
 			LogSystem.LogDBException(e);
 			return false;
 		}
 		return true;
 	}
}
