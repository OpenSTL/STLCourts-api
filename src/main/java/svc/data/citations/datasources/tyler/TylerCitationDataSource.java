package svc.data.citations.datasources.tyler;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import svc.data.citations.CitationDataSource;
import svc.models.Citation;

@Repository
public class TylerCitationDataSource implements CitationDataSource {

	@Autowired
	private TylerConfiguration tylerConfiguration;

	@Autowired
	private RestTemplate restTemplate;

	@SuppressWarnings("unused")
	@Override
	public List<Citation> getByCitationNumberAndDOB(String citationNumber, Date dob) {

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(tylerConfiguration.rootUrl)
				.queryParam("citationNumber", citationNumber).queryParam("dob", dob);
		HttpHeaders headers = new HttpHeaders();
		headers.add("apikey", tylerConfiguration.apiKey);
		HttpEntity<?> query = new HttpEntity<>(headers);
		ResponseEntity<List<TylerCitation>> tylerCitationsResponse = null;
		ParameterizedTypeReference<List<TylerCitation>> type = new ParameterizedTypeReference<List<TylerCitation>>() {
		};
		tylerCitationsResponse = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, query, type);
		List<TylerCitation> tylerCitations = tylerCitationsResponse.getBody();

		// TODO
		// This is close. It's getting 400s. Probably malformed requests.
		// I give up for the night. Your turn.

		return null;
	}

	@Override
	public List<Citation> getByLicenseAndDOB(String driversLicenseNumber, Date dob) {
		return null; // TODO
	}

	@Override
	public List<Citation> getByNameAndMunicipalitiesAndDOB(String lastName, List<Long> municipalities, Date dob) {
		return null; // TODO
	}
}
