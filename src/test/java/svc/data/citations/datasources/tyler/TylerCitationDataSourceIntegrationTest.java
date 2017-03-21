package svc.data.citations.datasources.tyler;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import svc.Application;

@SpringBootTest
@ContextConfiguration(classes = Application.class, initializers = ConfigFileApplicationContextInitializer.class)
@RunWith(SpringRunner.class)
public class TylerCitationDataSourceIntegrationTest {

	@Autowired
	private TylerCitationDataSource tylerCitationDataSource;

	@Test
	public void test() {
		tylerCitationDataSource.getByCitationNumberAndDOB("1", new Date());
	}
}
