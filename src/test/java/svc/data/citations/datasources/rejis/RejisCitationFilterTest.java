package svc.data.citations.datasources.rejis;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Lists;

import svc.data.citations.datasources.rejis.models.RejisPartialCitation;

@RunWith(MockitoJUnitRunner.class)
public class RejisCitationFilterTest {
	@InjectMocks
	RejisCitationFilter rejisCitationFilter;
	
	
	@Test
	public void correctlyFiltersCitations(){
		RejisPartialCitation CITATION1 = new RejisPartialCitation();
		CITATION1.caseNumber = "123";
		CITATION1.caseStatus = "A";
		
		RejisPartialCitation CITATION2 = new RejisPartialCitation();
		CITATION2.caseNumber = "1234";
		CITATION2.caseStatus = "C";
		
		List<RejisPartialCitation> CITATIONS = Lists.newArrayList(CITATION1,CITATION2);

		assertThat(rejisCitationFilter.Filter(CITATIONS).size(), is(1));
	}
}
