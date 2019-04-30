package svc.data.citations.datasources.importedITI;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

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
import svc.data.citations.datasources.importedITI.models.ImportedItiCitation;
import svc.data.citations.datasources.importedITI.transformers.ImportedItiCitationTransformer;
import svc.data.citations.datasources.transformers.CourtIdTransformer;
import svc.data.citations.filters.CitationFilter;
import svc.models.Citation;

@Repository
public class ImportedItiCitationDataSource implements CitationDataSource {

	@Autowired
	private ImportedItiConfiguration importedItiConfiguration;

	@Autowired
	private ImportedItiCitationTransformer citationTransformer;
	
	@Autowired
	private CourtIdTransformer courtIdTransformer;
	
	@Autowired
	private CitationFilter citationFilter;

	@Autowired
	private RestTemplate restTemplate;

	static private DateTimeFormatter dobFormatter = DateTimeFormatter.ofPattern("uuuu-MM-dd");

	@Override
	public List<Citation> getByCitationNumberAndDOB(String citationNumber, LocalDate dob) {

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(importedItiConfiguration.rootUrl)
				.queryParam("citationNumber", citationNumber).queryParam("dob", dob.format(dobFormatter));

		return performRestTemplateCall(builder.build().encode().toUri(), dob, null);
	}

	@Override
	public List<Citation> getByLicenseAndDOBAndLastName(String driversLicenseNumber, String driversLicenseState, LocalDate dob, String lastName) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(importedItiConfiguration.rootUrl)
				.queryParam("licenseNumber", driversLicenseNumber).queryParam("dob", dob.format(dobFormatter))
				.queryParam("licenseState", driversLicenseState).queryParam("lastName", lastName);

		return performRestTemplateCall(builder.build().encode().toUri(), dob, lastName);
	}

	@Override
	public List<Citation> getByNameAndMunicipalitiesAndDOB(String lastName, List<Long> municipalities, LocalDate dob) {
		
		List<Long> courtIds = courtIdTransformer.getCourtIdsFromMunicipalityIds(municipalities);
		List<String> courtIds_string = courtIds.stream().map(Object::toString).collect(Collectors.toList());
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(importedItiConfiguration.rootUrl)
				.queryParam("lastName", lastName).queryParam("dob", dob.format(dobFormatter)).queryParam("courtIds",String.join(",",courtIds_string));

		return performRestTemplateCall(builder.build().encode().toUri(), dob, lastName);
	}

	private List<Citation> performRestTemplateCall(URI uri, LocalDate dob, String lastName) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("apikey", importedItiConfiguration.apiKey);
		HttpEntity<?> query = new HttpEntity<>(headers);
		ResponseEntity<List<ImportedItiCitation>> importedCitationsResponse = null;
		ParameterizedTypeReference<List<ImportedItiCitation>> type = new ParameterizedTypeReference<List<ImportedItiCitation>>() {
		};

		List<ImportedItiCitation> importedItiCitations = null;
		try {
			importedCitationsResponse = restTemplate.exchange(uri, HttpMethod.GET, query, type);
			importedItiCitations = importedCitationsResponse.getBody();
			return citationFilter.Filter(citationTransformer.fromImportedItiCitations(importedItiCitations), lastName);
		} catch (RestClientException ex) {
			System.out.println("Imported ITI datasource is down.");
			return Lists.newArrayList();
		}
	}
	
}
