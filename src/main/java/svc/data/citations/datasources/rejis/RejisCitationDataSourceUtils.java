package svc.data.citations.datasources.rejis;

import java.net.URI;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.common.collect.Lists;

import rx.Observable;
import svc.data.citations.datasources.rejis.models.RejisCaseList;
import svc.data.citations.datasources.rejis.models.RejisFullCitation;
import svc.data.citations.datasources.rejis.models.RejisPartialCitation;
import svc.data.citations.datasources.rejis.transformers.RejisCitationTransformer;
import svc.logging.LogSystem;
import svc.models.Citation;

@Component
public class RejisCitationDataSourceUtils {

	@Autowired
	private RejisConfiguration rejisConfiguration;

	@Autowired
	private RejisCitationTransformer citationTransformer;
	
	@Autowired
	private RestTemplate restTemplate;
	
	public UriComponentsBuilder getCitationNumberBuilder(int pageNumber, String citationNumber, LocalDate dob, List<String> municipalityCodes){
		UriComponentsBuilder builder =	 UriComponentsBuilder.fromHttpUrl(rejisConfiguration.getRootUrl()+"/"+RejisConstants.BY_TICKET)
				.queryParam(RejisConstants.TICKET_NUMBER, citationNumber)
				.queryParam(RejisConstants.DOB, dob.toString())
				.queryParam(RejisConstants.AGENCY_ID,getMunicipalitiesString(municipalityCodes))
				.queryParam(RejisConstants.PAGE_NUMBER, pageNumber)
				.queryParam(RejisConstants.ROWS_PER_PAGE, 50)
				.queryParam(RejisConstants.RESULT_FORMAT,"json");
		
		return builder;
	}
	
	public UriComponentsBuilder getLicenseBuilder(int pageNumber, String dlNum, String dlState, String lastName, LocalDate dob, List<String> municipalityCodes){
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(rejisConfiguration.getRootUrl()+"/"+RejisConstants.BY_VEHICLE_LICENSE)
				.queryParam(RejisConstants.VEHICLE_LIC_NUMBER, dlNum)
				.queryParam(RejisConstants.VEHICLE_LIC_STATE, dlState)
				.queryParam(RejisConstants.DOB, dob.toString())
				.queryParam(RejisConstants.LAST_NAME, lastName)
				.queryParam(RejisConstants.AGENCY_ID,getMunicipalitiesString(municipalityCodes))
				.queryParam(RejisConstants.PAGE_NUMBER, pageNumber)
				.queryParam(RejisConstants.ROWS_PER_PAGE, 50)
				.queryParam(RejisConstants.RESULT_FORMAT,"json");
		
		return builder;
	}

	public UriComponentsBuilder getNameBuilder(int pageNumber, String lastName, LocalDate dob, List<String> municipalityCodes){
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(rejisConfiguration.getRootUrl()+"/"+RejisConstants.BY_NAME)
				.queryParam(RejisConstants.LAST_NAME, lastName)
				.queryParam(RejisConstants.DOB, dob.toString())
				.queryParam(RejisConstants.AGENCY_ID,getMunicipalitiesString(municipalityCodes))
				.queryParam(RejisConstants.PAGE_NUMBER, pageNumber)
				.queryParam(RejisConstants.ROWS_PER_PAGE, 50)
				.queryParam(RejisConstants.RESULT_FORMAT,"json");
		
		return builder;
	}

	private HttpEntity<?> getHttpEntity(){
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", rejisConfiguration.apiKey);
		return new HttpEntity<>(headers);
	}
	
	public RejisCaseList performRestTemplateCall(URI uri) {
		RejisCaseList rejisCaseList = null;
		
		try{
			ResponseEntity<RejisCaseList> response = restTemplate.exchange(uri, HttpMethod.GET, getHttpEntity(), RejisCaseList.class);
			rejisCaseList = response.getBody();
		}catch(Exception e){
			LogSystem.LogCitationDataSourceException("Rejis datasource exception: " + e.getMessage());
		}
		
		return rejisCaseList;
	}
	
	public List<Citation> getFullCitations(List<RejisPartialCitation> partialCitations){
		List<Observable<Citation>> citations = Lists.newArrayList();
		for(RejisPartialCitation partialCitation: partialCitations){
			citations.add(Observable.from(getCitation(partialCitation)).onExceptionResumeNext(Observable.just(null)));
		}
		return Observable.merge(citations).onExceptionResumeNext(Observable.just(null)).toList().toBlocking().first();
	}
	
	private RejisFullCitation getFullCaseView(RejisPartialCitation partialCitation){
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(rejisConfiguration.getRootUrl()+"/"+RejisConstants.BY_CASE)
				.queryParam(RejisConstants.AGENCY_ID, partialCitation.agencyId)
				.queryParam(RejisConstants.CASE_NUMBER, partialCitation.caseNumber);
		
		RejisFullCitation rejisFullCitation = null;
		
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
		if (rejisFullCitation == null){
			return Lists.newArrayList();
		}
		Citation citation = citationTransformer.fromRejisFullCitation(rejisFullCitation, partialCitation);
		return Arrays.asList(citation);
	}
	
	private String getMunicipalitiesString(List<String> municipalityCodes){
		StringJoiner joiner  = new StringJoiner(",");
        for(String muni : municipalityCodes) { joiner.add(muni); }
        
        return joiner.toString();
	}
}
