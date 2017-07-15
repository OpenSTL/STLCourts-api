package svc.data.citations.datasources.rejis;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.common.collect.Lists;

import rx.Observable;
import svc.data.citations.CitationDataSource;
import svc.data.citations.datasources.rejis.models.RejisCaseList;
import svc.data.citations.datasources.rejis.models.RejisFullCitation;
import svc.data.citations.datasources.rejis.models.RejisPartialCitation;
import svc.data.citations.datasources.rejis.transformers.RejisCitationTransformer;
import svc.data.citations.filters.CitationFilter;
import svc.logging.LogSystem;
import svc.models.Citation;

@Repository
public class RejisCitationDataSource implements CitationDataSource {

	@Autowired
	private RejisConfiguration rejisConfiguration;

	@Autowired
	private RejisCitationTransformer citationTransformer;
	
	@Autowired
	private CitationFilter citationFilter;

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private RejisMunicipalityCodesFactory municipalityCodesFactory;

	static private DateTimeFormatter dobFormatter = DateTimeFormatter.ofPattern("uuuu");

	@Override
	public List<Citation> getByCitationNumberAndDOB(String citationNumber, LocalDate dob) {
		List<String> municipalityCodes = municipalityCodesFactory.getAllMunicipalityCodes();
		RejisCaseList rejisCaseList;
		List<RejisPartialCitation> regisPartialCitations = new ArrayList<>();
		
		int pageNumber = 0;
		do{
			pageNumber++;
			UriComponentsBuilder builder = getCitationNumberBuilder(pageNumber,citationNumber);
			rejisCaseList = performRestTemplateCall(builder.build().encode().toUri());
			regisPartialCitations.addAll(rejisCaseList.CaseIndexRows);
		}while (rejisCaseList.TotalPages > pageNumber);
		
		return getFullCitations(regisPartialCitations);
	}
	
	private UriComponentsBuilder getCitationNumberBuilder(int pageNumber, String citationNumber){
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(rejisConfiguration.rootUrl+"/ByTicket")
				.queryParam("CaseOrTicketNum", citationNumber)
				.queryParam("AgcyIdOri","SL")
				.queryParam("PageNum", pageNumber)
				.queryParam("RowsPerPage", 50)
				.queryParam("ResultFormat","json");
		
		return builder;
	}

	@Override
	public List<Citation> getByLicenseAndDOB(String driversLicenseNumber, String driversLicenseState, LocalDate dob) {
		List<String> municipalityCodes = municipalityCodesFactory.getAllMunicipalityCodes();
		
		RejisCaseList rejisCaseList;
		List<RejisPartialCitation> regisPartialCitations = new ArrayList<>();
		
		int pageNumber = 0;
		do{
			pageNumber++;
			UriComponentsBuilder builder = getLicenseBuilder(pageNumber,driversLicenseNumber, driversLicenseState);
			rejisCaseList = performRestTemplateCall(builder.build().encode().toUri());
			regisPartialCitations.addAll(rejisCaseList.CaseIndexRows);
		}while (rejisCaseList.TotalPages > pageNumber);
		
		return getFullCitations(regisPartialCitations);
	}
	
	private UriComponentsBuilder getLicenseBuilder(int pageNumber, String dlNum, String dlState){
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(rejisConfiguration.rootUrl+"/ByVehicleLic")
				.queryParam("VehicleLicNum", dlNum)
				.queryParam("VehicleLicState", dlState)
//				.queryParam("LastName", lastName) //yikes
				.queryParam("AgcyIdOri","SL")
				.queryParam("PageNum", pageNumber)
				.queryParam("RowsPerPage", 50)
				.queryParam("ResultFormat","json");
		
		return builder;
	}

	@Override
	public List<Citation> getByNameAndMunicipalitiesAndDOB(String lastName, List<Long> municipalities, LocalDate dob) {
		List<String> municipalityCodes = municipalityCodesFactory.getMunicipalityCodesForMunicipalities(municipalities);
		
		RejisCaseList rejisCaseList;
		List<RejisPartialCitation> regisPartialCitations = new ArrayList<>();
		
		int pageNumber = 0;
		do{
			pageNumber++;
			UriComponentsBuilder builder = getNameBuilder(pageNumber,lastName, dob);
			rejisCaseList = performRestTemplateCall(builder.build().encode().toUri());
			regisPartialCitations.addAll(rejisCaseList.CaseIndexRows);
		}while (rejisCaseList.TotalPages > pageNumber);
		
		return getFullCitations(regisPartialCitations);
	}
	

	private UriComponentsBuilder getNameBuilder(int pageNumber, String lastName, LocalDate dob){
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(rejisConfiguration.rootUrl+"/byName")
				.queryParam("LastName", lastName)
				.queryParam("DobYear", dob.format(dobFormatter))
				.queryParam("AgcyIdOri","SL")
				.queryParam("PageNum", pageNumber)
				.queryParam("RowsPerPage", 50)
				.queryParam("ResultFormat","json");
		
		return builder;
	}

	private HttpEntity<?> getHttpEntity(){
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", rejisConfiguration.apiKey);
		return new HttpEntity<>(headers);
	}
	
	private RejisCaseList performRestTemplateCall(URI uri) {
		RejisCaseList rejisCaseList = null;
		
		try{
			ResponseEntity<RejisCaseList> response = restTemplate.exchange(uri, HttpMethod.GET, getHttpEntity(), RejisCaseList.class);
			rejisCaseList = response.getBody();
		}catch(Exception e){
			LogSystem.LogCitationDataSourceException("Rejis datasource exception: " + e.getMessage());
		}
		
		return rejisCaseList;
	}
	
	private List<Citation> getFullCitations(List<RejisPartialCitation> partialCitations){
		
		List<Observable<Citation>> citations = Lists.newArrayList();
		for(RejisPartialCitation partialCitation: partialCitations){
			citations.add(Observable.from(getCitation(partialCitation)).onExceptionResumeNext(Observable.just(null)));
		}
		return Observable.merge(citations).onExceptionResumeNext(Observable.just(null)).toList().toBlocking().first();
	}
	
	private RejisFullCitation getFullCaseView(RejisPartialCitation partialCitation){
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(rejisConfiguration.rootUrl+"/CaseView")
				.queryParam("AgcyIdOri",partialCitation.AgcyId)
				.queryParam("CaseNumber", partialCitation.CaseNum);
		
		RejisFullCitation rejisFullCitation = new RejisFullCitation();
		
		try{
			ResponseEntity<RejisFullCitation> response = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, getHttpEntity(), RejisFullCitation.class);
			rejisFullCitation = response.getBody();
		}catch(Exception e){
			LogSystem.LogCitationDataSourceException("Rejis datasource exception: " + e.getMessage());
		}
		
		return rejisFullCitation;
	}
	
	private List<Citation> getCitation(RejisPartialCitation partialCitation){
		RejisFullCitation rejisFullCitation = getFullCaseView(partialCitation);
		Citation citation = citationTransformer.fromRejisFullCitation(rejisFullCitation, partialCitation);
		return Arrays.asList(citation);
	}
}