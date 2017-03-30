package svc.data.citations.datasources.tyler;

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
import svc.data.citations.datasources.CITATION_DATASOURCE;
import svc.models.Citation;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Repository
public class TylerCitationDataSource implements CitationDataSource {

	@Autowired
	private TylerConfiguration tylerConfiguration;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public CITATION_DATASOURCE getCitationDataSource(){
		return CITATION_DATASOURCE.TYLER;
	}
	
	@SuppressWarnings("unused")
	@Override
	public List<Citation> getByCitationNumberAndDOB(String citationNumber, LocalDate dob) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(tylerConfiguration.rootUrl)
				.queryParam("citationNumber", citationNumber).queryParam("dob", Date.valueOf(dob));
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
	public List<Citation> getByLicenseAndDOB(String driversLicenseNumber, LocalDate dob) {
		return null; // TODO
	}

	@Override
	public List<Citation> getByNameAndMunicipalitiesAndDOB(String lastName, List<Long> municipalities, LocalDate dob) {
		return null; // TODO
	}
}
