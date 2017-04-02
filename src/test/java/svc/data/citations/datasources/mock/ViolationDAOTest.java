package svc.data.citations.datasources.mock;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.google.common.collect.Lists;

import svc.data.citations.datasources.mock.ViolationDAO;
import svc.models.Violation;

@RunWith(MockitoJUnitRunner.class)
public class ViolationDAOTest {
	@InjectMocks
	ViolationDAO mockViolationDAO;
	
	@Mock
    NamedParameterJdbcTemplate jdbcTemplate;
    
	@SuppressWarnings("unchecked")
	@Test
	public void returnsViolationGivenCitationNumber	(){
		final String CITATIONNUMBER = "F3453";
		final Violation VIOLATION = new Violation();
        VIOLATION.id = 4;
        final List<Violation> VIOLATIONS = Lists.newArrayList(VIOLATION);
        
        when(jdbcTemplate.query(Matchers.anyString(), Matchers.anyMap(), Matchers.<RowMapper<Violation>>any()))
        .thenReturn(Lists.newArrayList(VIOLATIONS));
        
        List<Violation> violations = mockViolationDAO.getViolationsByCitationNumber(CITATIONNUMBER);
        assertThat(violations.get(0).id, is(4));
	}
	
	@SuppressWarnings("unchecked")
	@Ignore
	@Test
	public void insertsViolations(){
		//This code is not working yet, to Ignored.
		final Violation VIOLATION = new Violation();
        VIOLATION.id = 4;
        final List<Violation> VIOLATIONS = Lists.newArrayList(VIOLATION);
        
        //when(jdbcTemplate.execute(Matchers.anyString(), Mockito.any(PreparedStatementCallback.class))).thenReturn(true);
        when(jdbcTemplate.execute(Matchers.anyString(), Mockito.any(PreparedStatementCallback.class)))
        .thenAnswer(new Answer<Boolean>(){

			@Override
			public Boolean answer(InvocationOnMock invocation) throws Throwable {
				// TODO Auto-generated method stub
				//http://stackoverflow.com/questions/28633173/increasing-code-coverage-for-jdbctemplate-mocking
				return true;
			}
        	
        });
        
        Boolean returnValue = mockViolationDAO.insertViolations(VIOLATIONS);
        assertThat(returnValue, is(true));
	}
	
}
