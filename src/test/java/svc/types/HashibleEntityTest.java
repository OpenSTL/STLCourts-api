package svc.types;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import svc.models.Court;

@RunWith(MockitoJUnitRunner.class)
public class HashibleEntityTest {
	@Test
	public void initializesProperly(){
		HashableEntity<Court> hashableEntity = new HashableEntity<Court>(Court.class,5L);
		assertThat(hashableEntity.getValue(),equalTo(5L));
		assertThat(hashableEntity.getType(),equalTo(Court.class));
	}
	
	@Test
	public void setsValueProperly(){
		HashableEntity<Court> hashableEntity = new HashableEntity<Court>(Court.class,5L);
		hashableEntity.setValue(6L);
		assertThat(hashableEntity.getValue(),equalTo(6L));
	}
}
