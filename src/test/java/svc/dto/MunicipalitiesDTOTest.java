package svc.dto;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import org.junit.Assert;
import svc.BaseTest;
import svc.models.Municipality;

public class MunicipalitiesDTOTest extends BaseTest {
	
	@Test
	public void setsMunicipalitiesOnConstruction() {
		List<Municipality> municipalities = new ArrayList<>();
		municipalities.add(new Municipality());
		municipalities.add(new Municipality());
		
		MunicipalitiesDTO dto = new MunicipalitiesDTO(municipalities);

		Assert.assertEquals(dto.municipalities.size(), 2);
	}
}
