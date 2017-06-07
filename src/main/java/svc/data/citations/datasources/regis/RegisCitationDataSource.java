package svc.data.citations.datasources.regis;

import java.time.LocalDate;
import java.util.List;

import javax.xml.bind.JAXBElement;

import org.springframework.stereotype.Repository;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import svc.data.citations.CitationDataSource;
import svc.data.citations.datasources.regis.org.tempuri.*;
import svc.models.Citation;

//TODO:  Add a filter to make sure that results match dob.
@Repository
public class RegisCitationDataSource extends WebServiceGatewaySupport implements CitationDataSource{

	private String REJIS_SERVICE = "http://rejspaapp1/municourtservices/MuniCourt.svc";
	private String LIC_STATE_ACTION = "http://tempuri.org/IMuniCourt/GetByLicState";
	private String CITATION_ACTION = "http://tempuri.org/IMuniCourt/GetByTicketNumber";
	private String NAME_MUNICIPALITY_ACTION = "http://tempuri.org/IMuniCourt/GetByLastFirstDob";
	@Override
	public List<Citation> getByCitationNumberAndDOB(String citationNumber, LocalDate dob) {
		ObjectFactory objectFactory = new ObjectFactory();
		JAXBElement<String> jaxbCitationNumber = objectFactory.createGetByTicketNumberPTicketNum(citationNumber);
		
		GetByTicketNumber request = new GetByTicketNumber();
		request.setPTicketNum(jaxbCitationNumber);
		
		GetByTicketNumberResponse response = (GetByTicketNumberResponse) getWebServiceTemplate()
																.marshalSendAndReceive(REJIS_SERVICE,
																		request,
																		new SoapActionCallback(CITATION_ACTION));
		return null;
	}

	@Override
	public List<Citation> getByLicenseAndDOB(String driversLicenseNumber, String driversLicenseState, LocalDate dob) {
		ObjectFactory objectFactory = new ObjectFactory();
		JAXBElement<String> jaxbDriversLicenseNumber = objectFactory.createGetByLicStatePVehLicNum(driversLicenseNumber);
		JAXBElement<String> jaxbDriversLicenseState = objectFactory.createGetByLicStatePVehLicState(driversLicenseState);
		
		GetByLicState request = new GetByLicState();
		request.setPVehLicNum(jaxbDriversLicenseNumber);
		request.setPVehLicState(jaxbDriversLicenseState);
		
		GetByLicStateResponse response = (GetByLicStateResponse) getWebServiceTemplate()
																.marshalSendAndReceive(REJIS_SERVICE,
																		request,
																		new SoapActionCallback(LIC_STATE_ACTION));
		
		return null;
	}

	@Override
	public List<Citation> getByNameAndMunicipalitiesAndDOB(String lastName, List<Long> municipalities, LocalDate dob) {
		ObjectFactory objectFactory = new ObjectFactory();
		JAXBElement<String> jaxbLastName = objectFactory.createGetByLastFirstDobPLastName(lastName);
		
		
		GetByLicState request = new GetByLicState();
		request.setPLastName(jaxbLastName);
		
		GetByLastFirstDob response = (GetByLastFirstDob) getWebServiceTemplate()
																.marshalSendAndReceive(REJIS_SERVICE,
																		request,
																		new SoapActionCallback(NAME_MUNICIPALITY_ACTION));
		return null;
	}

}
