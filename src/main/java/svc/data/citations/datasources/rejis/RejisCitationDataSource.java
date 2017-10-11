package svc.data.citations.datasources.rejis;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Lists;

import svc.data.citations.CitationDataSource;
import svc.data.citations.datasources.rejis.models.RejisCaseList;
import svc.data.citations.datasources.rejis.models.RejisPartialCitation;
import svc.data.citations.datasources.rejis.models.RejisQueryObject;
import svc.data.citations.filters.CitationFilter;
import svc.models.Citation;

@Repository
public class RejisCitationDataSource implements CitationDataSource {

	@Autowired
	private RejisApiCalls rejisApiCalls;
	
	@Autowired
	private CitationFilter citationFilter;
	
	@Autowired
	private RejisMunicipalityCodesFactory municipalityCodesFactory;
	
	@Autowired
	private RejisUriBuilder rejisUriBuilder;

	
	@Override
	public List<Citation> getByCitationNumberAndDOB(String citationNumber, LocalDate dob) {
		List<String> municipalityCodes = municipalityCodesFactory.getAllMunicipalityCodes();
		RejisCaseList rejisCaseList;
		List<RejisPartialCitation> rejisPartialCitations = new ArrayList<>();
		
		int pageNumber = 0;
		do{
			pageNumber++;
			RejisQueryObject rejisQueryObject = (new RejisQueryObject()).loadForTicket(pageNumber, citationNumber, dob, municipalityCodes);
			rejisCaseList = rejisApiCalls.getRejisCaseList(rejisUriBuilder.createURI(rejisQueryObject));
			if (rejisCaseList == null){
				return Lists.newArrayList();
			}
			rejisPartialCitations.addAll(rejisCaseList.caseIndexRows);
		}while (rejisCaseList.totalPages > pageNumber);
	
		return citationFilter.Filter(rejisApiCalls.getFullCitations(rejisPartialCitations), null);
	}

	@Override
	public List<Citation> getByLicenseAndDOBAndLastName(String driversLicenseNumber, String driversLicenseState, LocalDate dob, String lastName) {
		List<String> municipalityCodes = municipalityCodesFactory.getAllMunicipalityCodes();
		
		RejisCaseList rejisCaseList;
		List<RejisPartialCitation> regisPartialCitations = new ArrayList<>();
		
		int pageNumber = 0;
		do{
			pageNumber++;
			RejisQueryObject rejisQueryObject = (new RejisQueryObject()).loadForLicense(pageNumber, driversLicenseNumber, driversLicenseState, lastName, dob, municipalityCodes);
			rejisCaseList = rejisApiCalls.getRejisCaseList(rejisUriBuilder.createURI(rejisQueryObject));
			if (rejisCaseList == null){
				return Lists.newArrayList();
			}
			regisPartialCitations.addAll(rejisCaseList.caseIndexRows);
		}while (rejisCaseList.totalPages > pageNumber);
		
		return citationFilter.Filter(rejisApiCalls.getFullCitations(regisPartialCitations), lastName);
	}
	
	@Override
	public List<Citation> getByNameAndMunicipalitiesAndDOB(String lastName, List<Long> municipalities, LocalDate dob) {
		List<String> municipalityCodes = municipalityCodesFactory.getMunicipalityCodesForMunicipalities(municipalities);
		
		RejisCaseList rejisCaseList;
		List<RejisPartialCitation> regisPartialCitations = new ArrayList<>();
		
		int pageNumber = 0;
		do{
			pageNumber++;
			RejisQueryObject rejisQueryObject = (new RejisQueryObject()).loadForName(pageNumber, lastName, dob, municipalityCodes);
			rejisCaseList = rejisApiCalls.getRejisCaseList(rejisUriBuilder.createURI(rejisQueryObject));
			if (rejisCaseList == null){
				return Lists.newArrayList();
			}
			regisPartialCitations.addAll(rejisCaseList.caseIndexRows);
		}while (rejisCaseList.totalPages > pageNumber);
		
		return citationFilter.Filter(rejisApiCalls.getFullCitations(regisPartialCitations), lastName);
	}

}
