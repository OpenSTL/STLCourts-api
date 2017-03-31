package svc.data.citations.datasources.tyler;

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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import svc.data.citations.CitationDataSource;
import svc.models.Citation;

@Repository
public class TylerCitationDataSource implements CitationDataSource {

	@Autowired
	private TylerConfiguration tylerConfiguration;

	@Autowired
	private TylerTransformer tylerTransformer;

	@Autowired
	private RestTemplate restTemplate;

	private DateTimeFormatter dobFormatter = DateTimeFormatter.ofPattern("MM/dd/uuuu");

	@Override
	public List<Citation> getByCitationNumberAndDOB(String citationNumber, LocalDate dob) {

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(tylerConfiguration.rootUrl)
				.queryParam("citationNumber", citationNumber).queryParam("dob", dob.format(dobFormatter));
		HttpHeaders headers = new HttpHeaders();
		headers.add("apikey", tylerConfiguration.apiKey);
		HttpEntity<?> query = new HttpEntity<>(headers);
		ResponseEntity<List<TylerCitation>> tylerCitationsResponse = null;
		ParameterizedTypeReference<List<TylerCitation>> type = new ParameterizedTypeReference<List<TylerCitation>>() {
		};
		URI uri = builder.build().encode().toUri();
		List<TylerCitation> tylerCitations = null;
		try {
			tylerCitationsResponse = restTemplate.exchange(uri, HttpMethod.GET, query, type);
			tylerCitations = tylerCitationsResponse.getBody();
		} catch (HttpClientErrorException ex) {
			System.out.println(ex.getMessage());
			System.out.print(ex.getResponseBodyAsString());
		}

		return tylerTransformer.citationsFromTylerCitations(tylerCitations);
	}

	@Override
	public List<Citation> getByLicenseAndDOB(String driversLicenseNumber, LocalDate dob) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(tylerConfiguration.rootUrl)
				.queryParam("licenseNumber", driversLicenseNumber).queryParam("dob", dob.format(dobFormatter))
				.queryParam("licenseState", "MO");
		HttpHeaders headers = new HttpHeaders();
		headers.add("apikey", tylerConfiguration.apiKey);
		HttpEntity<?> query = new HttpEntity<>(headers);
		ResponseEntity<List<TylerCitation>> tylerCitationsResponse = null;
		ParameterizedTypeReference<List<TylerCitation>> type = new ParameterizedTypeReference<List<TylerCitation>>() {
		};
		URI uri = builder.build().encode().toUri();
		List<TylerCitation> tylerCitations = null;
		try {
			tylerCitationsResponse = restTemplate.exchange(uri, HttpMethod.GET, query, type);
			tylerCitations = tylerCitationsResponse.getBody();
		} catch (HttpClientErrorException ex) {
			System.out.println(ex.getMessage());
			System.out.print(ex.getResponseBodyAsString());
		}

		return tylerTransformer.citationsFromTylerCitations(tylerCitations);
	}

	@Override
	public List<Citation> getByNameAndMunicipalitiesAndDOB(String lastName, List<Long> municipalities, LocalDate dob) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(tylerConfiguration.rootUrl)
				.queryParam("lastName", lastName).queryParam("dob", dob.format(dobFormatter));
		HttpHeaders headers = new HttpHeaders();
		headers.add("apikey", tylerConfiguration.apiKey);
		HttpEntity<?> query = new HttpEntity<>(headers);
		ResponseEntity<List<TylerCitation>> tylerCitationsResponse = null;
		ParameterizedTypeReference<List<TylerCitation>> type = new ParameterizedTypeReference<List<TylerCitation>>() {
		};
		URI uri = builder.build().encode().toUri();
		List<TylerCitation> tylerCitations = null;
		try {
			tylerCitationsResponse = restTemplate.exchange(uri, HttpMethod.GET, query, type);
			tylerCitations = tylerCitationsResponse.getBody();
		} catch (HttpClientErrorException ex) {
			System.out.println(ex.getMessage());
			System.out.print(ex.getResponseBodyAsString());
		}

		return tylerTransformer.citationsFromTylerCitations(tylerCitations);
	}
}
