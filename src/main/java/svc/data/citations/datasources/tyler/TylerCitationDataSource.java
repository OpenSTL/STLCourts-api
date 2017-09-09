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
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.common.collect.Lists;

import svc.data.citations.CitationDataSource;
import svc.data.citations.datasources.tyler.models.TylerCitation;
import svc.data.citations.datasources.tyler.transformers.TylerCitationTransformer;
import svc.data.citations.filters.CitationFilter;
import svc.models.Citation;

@Repository
public class TylerCitationDataSource implements CitationDataSource {

	@Autowired
	private TylerConfiguration tylerConfiguration;

	@Autowired
	private TylerCitationTransformer citationTransformer;
	
	@Autowired
	private CitationFilter citationFilter;

	@Autowired
	private RestTemplate restTemplate;

	static private DateTimeFormatter dobFormatter = DateTimeFormatter.ofPattern("MM/dd/uuuu");

	@Override
	public List<Citation> getByCitationNumberAndDOB(String citationNumber, LocalDate dob) {

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(tylerConfiguration.rootUrl)
				.queryParam("citationNumber", citationNumber).queryParam("dob", dob.format(dobFormatter));

		return performRestTemplateCall(builder.build().encode().toUri(), dob, null);
	}

	@Override
	public List<Citation> getByLicenseAndDOBAndLastName(String driversLicenseNumber, String driversLicenseState, LocalDate dob, String lastName) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(tylerConfiguration.rootUrl)
				.queryParam("licenseNumber", driversLicenseNumber).queryParam("dob", dob.format(dobFormatter))
				.queryParam("licenseState", driversLicenseState).queryParam("lastName", lastName);

		return performRestTemplateCall(builder.build().encode().toUri(), dob, lastName);
	}

	@Override
	public List<Citation> getByNameAndMunicipalitiesAndDOB(String lastName, List<Long> municipalities, LocalDate dob) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(tylerConfiguration.rootUrl)
				.queryParam("lastName", lastName).queryParam("dob", dob.format(dobFormatter));

		return performRestTemplateCall(builder.build().encode().toUri(), dob, lastName);
	}

	private List<Citation> performRestTemplateCall(URI uri, LocalDate dob, String lastName) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("apikey", tylerConfiguration.apiKey);
		HttpEntity<?> query = new HttpEntity<>(headers);
		ResponseEntity<List<TylerCitation>> tylerCitationsResponse = null;
		ParameterizedTypeReference<List<TylerCitation>> type = new ParameterizedTypeReference<List<TylerCitation>>() {
		};

		List<TylerCitation> tylerCitations = null;
		try {
			tylerCitationsResponse = restTemplate.exchange(uri, HttpMethod.GET, query, type);
			tylerCitations = tylerCitationsResponse.getBody();
			return citationFilter.Filter(citationTransformer.fromTylerCitations(tylerCitations),dob, lastName);
		} catch (RestClientException ex) {
			System.out.println("Tyler datasource is down.");
			return Lists.newArrayList();
		}

		
	}
}
