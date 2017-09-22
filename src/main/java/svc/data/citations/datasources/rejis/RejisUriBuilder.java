package svc.data.citations.datasources.rejis;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import svc.data.citations.datasources.rejis.models.RejisQueryObject;

@Component
public class RejisUriBuilder {

	@Autowired
	private RejisConfiguration rejisConfiguration;


	public URI createURI(RejisQueryObject rejisQueryObject ){
		return createUriComponentsBuilder(rejisQueryObject).build().encode().toUri();
	}
	
	private UriComponentsBuilder createUriComponentsBuilder(RejisQueryObject rejisQueryObject){
		UriComponentsBuilder builder =	 UriComponentsBuilder.fromHttpUrl(rejisConfiguration.getRootUrl()+"/"+rejisQueryObject.getQueryType());
		
		switch(rejisQueryObject.getQueryType()){
		case RejisConstants.BY_TICKET:
			builder
			.queryParam(RejisConstants.TICKET_NUMBER, rejisQueryObject.getCitationNumber())
			.queryParam(RejisConstants.DOB, rejisQueryObject.getDob())
			.queryParam(RejisConstants.AGENCY_ID,rejisQueryObject.getMunicipalityCodes())
			.queryParam(RejisConstants.PAGE_NUMBER, rejisQueryObject.getPageNumber())
			.queryParam(RejisConstants.ROWS_PER_PAGE, 50)
			.queryParam(RejisConstants.RESULT_FORMAT,"json");
			break;
		case RejisConstants.BY_VEHICLE_LICENSE:
			builder
			.queryParam(RejisConstants.VEHICLE_LIC_NUMBER, rejisQueryObject.getDriversLicenseNumber())
			.queryParam(RejisConstants.VEHICLE_LIC_STATE, rejisQueryObject.getDriversLicenseState())
			.queryParam(RejisConstants.DOB, rejisQueryObject.getDob())
			.queryParam(RejisConstants.LAST_NAME, rejisQueryObject.getLastName())
			.queryParam(RejisConstants.AGENCY_ID,rejisQueryObject.getMunicipalityCodes())
			.queryParam(RejisConstants.PAGE_NUMBER, rejisQueryObject.getPageNumber())
			.queryParam(RejisConstants.ROWS_PER_PAGE, 50)
			.queryParam(RejisConstants.RESULT_FORMAT,"json");
			break;
		case RejisConstants.BY_NAME:
			builder
			.queryParam(RejisConstants.LAST_NAME, rejisQueryObject.getLastName())
			.queryParam(RejisConstants.DOB, rejisQueryObject.getDob())
			.queryParam(RejisConstants.AGENCY_ID,rejisQueryObject.getMunicipalityCodes())
			.queryParam(RejisConstants.PAGE_NUMBER, rejisQueryObject.getPageNumber())
			.queryParam(RejisConstants.ROWS_PER_PAGE, 50)
			.queryParam(RejisConstants.RESULT_FORMAT,"json");
			break;
		}
		
		return builder;
	}
}
