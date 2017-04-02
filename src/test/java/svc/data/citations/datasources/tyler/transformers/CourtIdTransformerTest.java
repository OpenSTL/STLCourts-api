package svc.data.citations.datasources.tyler.transformers;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import svc.models.Court;
import svc.types.HashableEntity;

@RunWith(MockitoJUnitRunner.class)
public class CourtIdTransformerTest {
	@InjectMocks
	CourtIdTransformer mockCourtIdTransformer;
	
	@Mock
	NamedParameterJdbcTemplate mockJdbcTemplate;
	
	@SuppressWarnings("unchecked")
	@Test
	public void returnsCourtIdFromTylerCourtIdentifier(){
		when(mockJdbcTemplate.queryForObject(anyString(),anyMap(),Matchers.<RowMapper<Long>>any())).thenReturn(5L);
		
		HashableEntity<Court> returnedCourtId = mockCourtIdTransformer.lookupCourtId("someIdentifier");
		assertThat(returnedCourtId.getValue(),is(5L));
	}

}
