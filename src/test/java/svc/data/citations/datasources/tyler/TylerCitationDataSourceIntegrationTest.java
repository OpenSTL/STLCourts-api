package svc.data.citations.datasources.tyler;

import java.time.LocalDate;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import svc.Application;
import svc.models.Citation;

@Ignore
@SpringBootTest
@ContextConfiguration(classes = Application.class, initializers = ConfigFileApplicationContextInitializer.class)
@RunWith(SpringRunner.class)
public class TylerCitationDataSourceIntegrationTest {

	@Autowired
	private TylerCitationDataSource tylerCitationDataSource;

	@SuppressWarnings("unused")
	@Test
	public void testCitationNumber() {
		List<Citation> citations = tylerCitationDataSource.getByCitationNumberAndDOB("", LocalDate.parse("1900-01-01"));
	}

	@SuppressWarnings("unused")
	@Test
	public void testDriversLicense() {
		List<Citation> citations = tylerCitationDataSource.getByLicenseAndDOB("", LocalDate.parse("1900-01-01"));
	}

	@SuppressWarnings("unused")
	@Test
	public void testName() {
		List<Citation> citations = tylerCitationDataSource.getByNameAndMunicipalitiesAndDOB("", null,
				LocalDate.parse("1900-01-01"));
	}
}
