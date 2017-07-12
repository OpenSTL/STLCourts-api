package svc.data.citations.datasources.rejis;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.common.collect.Lists;

import svc.data.citations.CitationDataSource;
import svc.data.citations.datasources.tyler.models.TylerCitation;
import svc.data.citations.datasources.tyler.transformers.CitationTransformer;
import svc.data.citations.filters.CitationFilter;
import svc.models.Citation;

@Repository
public class RejisCitationDataSource implements CitationDataSource {

	@Autowired
	private RejisConfiguration rejisConfiguration;

	@Autowired
	private CitationTransformer citationTransformer;
	
	@Autowired
	private CitationFilter citationFilter;

	@Autowired
	private RestTemplate restTemplate;

	static private DateTimeFormatter dobFormatter = DateTimeFormatter.ofPattern("MM/dd/uuuu");

	@Override
	public List<Citation> getByCitationNumberAndDOB(String citationNumber, LocalDate dob) {

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(rejisConfiguration.rootUrl+"/ByTicket")
				.queryParam("CaseOrTicketNum", citationNumber)
				.queryParam("AgcyIdOri",);

		return performRestTemplateCall(builder.build().encode().toUri());
	}

	@Override
	public List<Citation> getByLicenseAndDOB(String driversLicenseNumber, String driversLicenseState, LocalDate dob) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(rejisConfiguration.rootUrl+"/ByVehicleLic")
				.queryParam("VehicleLicNum", driversLicenseNumber)
				.queryParam("VehicleLicState", driversLicenseState)
				.queryParam("LastName", lastName) //yikes
				.queryParam("AgcyIdOri",);

		return performRestTemplateCall(builder.build().encode().toUri());
	}

	@Override
	public List<Citation> getByNameAndMunicipalitiesAndDOB(String lastName, List<Long> municipalities, LocalDate dob) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(rejisConfiguration.rootUrl+"/byName")
				.queryParam("LastName", lastName)
				.queryParam("DobYear", dob.format(dobFormatter))
				.queryParam("AgcyIdOri",);

		return performRestTemplateCall(builder.build().encode().toUri());
	}

	private List<Citation> performRestTemplateCall(URI uri) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", rejisConfiguration.apiKey);
		HttpEntity<?> query = new HttpEntity<>(headers);
		ResponseEntity<List<TylerCitation>> tylerCitationsResponse = null;
		ParameterizedTypeReference<List<TylerCitation>> type = new ParameterizedTypeReference<List<TylerCitation>>() {
		};

		List<TylerCitation> tylerCitations = null;
		try {
			tylerCitationsResponse = restTemplate.exchange(uri, HttpMethod.GET, query, type);
			tylerCitations = tylerCitationsResponse.getBody();
			return citationFilter.Filter(citationTransformer.fromTylerCitations(tylerCitations));
		} catch (RestClientException ex) {
			System.out.println("Tyler datasource is down.");
			return Lists.newArrayList();
		}

		
	}
}
