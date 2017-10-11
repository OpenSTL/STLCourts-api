package svc.data.citations.datasources.rejis;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

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
public class RejisApiCalls {

	@Autowired
	private RejisConfiguration rejisConfiguration;

	@Autowired
	private RejisCitationTransformer citationTransformer;
	
	@Autowired
	private RestTemplate restTemplate;

	private HttpEntity<?> getHttpEntity(){
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", rejisConfiguration.apiKey);
		return new HttpEntity<>(headers);
	}
	
	public RejisCaseList getRejisCaseList(URI uri) {
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
	
}
