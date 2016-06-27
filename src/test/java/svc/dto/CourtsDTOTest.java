package svc.dto;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import org.junit.Assert;
import svc.BaseTest;
import svc.models.Court;

public class CourtsDTOTest extends BaseTest {
	
	@Test
	public void setsCourtsOnConstruction() {
		List<Court> courts = new ArrayList<>();
		courts.add(new Court());
		courts.add(new Court());
		
		CourtsDTO dto = new CourtsDTO(courts);

		Assert.assertEquals(dto.courts.size(), 2);
	}
}
