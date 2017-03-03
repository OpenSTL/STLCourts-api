package svc.security;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import svc.models.Court;
import svc.models.Judge;
import svc.models.Municipality;

@RunWith(MockitoJUnitRunner.class)
public class HashUtilTest {
	@InjectMocks
	HashUtil hashUtil;
	
	Hashids courtHashids = Mockito.mock(Hashids.class);
	Hashids municipalityHashids = Mockito.mock(Hashids.class);
	Hashids judgeHashids = Mockito.mock(Hashids.class);

	@Test
	public void encodesLongValues(){
		String encodedString = "ABC";
		when(courtHashids.encode(5L)).thenReturn(encodedString);
		String hashedString = hashUtil.encode(Court.class, 5L);
		assertThat(hashedString,equalTo(encodedString));
		verify(courtHashids).encode(5L);
		
		when(municipalityHashids.encode(5L)).thenReturn(encodedString);
		hashedString = hashUtil.encode(Municipality.class, 5L);
		assertThat(hashedString,equalTo(encodedString));
		verify(municipalityHashids).encode(5L);
		
		when(judgeHashids.encode(5L)).thenReturn(encodedString);
		hashedString = hashUtil.encode(Judge.class, 5L);
		assertThat(hashedString,equalTo(encodedString));
		verify(judgeHashids).encode(5L);
	}
	
	@Test
	public void decodesLongValues(){
		String encodedString = "ABC";
		Long decodedValue = 5L;
		long[] decodedArray = new long[]{decodedValue};
		
		when(courtHashids.decode(encodedString)).thenReturn(decodedArray);
		long unHashedString = hashUtil.decode(Court.class, encodedString);
		assertThat(unHashedString,equalTo(decodedValue));
		verify(courtHashids).decode(encodedString);
		
		when(municipalityHashids.decode(encodedString)).thenReturn(decodedArray);
		unHashedString = hashUtil.decode(Municipality.class, encodedString);
		assertThat(unHashedString,equalTo(decodedValue));
		verify(municipalityHashids).decode(encodedString);
		
		when(judgeHashids.decode(encodedString)).thenReturn(decodedArray);
		unHashedString = hashUtil.decode(Judge.class, encodedString);
		assertThat(unHashedString,equalTo(decodedValue));
		verify(judgeHashids).decode(encodedString);
	}
}
