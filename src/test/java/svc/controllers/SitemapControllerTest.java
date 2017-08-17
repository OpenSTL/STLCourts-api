package svc.controllers;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.CoreMatchers.*;

import svc.managers.SitemapManager;
import svc.models.Sitemap;

@RunWith(MockitoJUnitRunner.class)
public class SitemapControllerTest {

	@InjectMocks
	SitemapController controller;
	
	@Mock
	SitemapManager managerMock;
	
	@Test
	public void returnsSitemap(){
		Sitemap SITEMAP = mock(Sitemap.class);
	
		when(managerMock.generate()).thenReturn(SITEMAP);
		ResponseEntity<?> response = controller.GenerateSiteMap();
		Sitemap body = (Sitemap) response.getBody();
		assertThat(body,equalTo(SITEMAP));
	}
	
	@Test
	public void returnsSitemapFromFileName() throws NotFoundException{
		Sitemap SITEMAP = mock(Sitemap.class);
	
		when(managerMock.generate()).thenReturn(SITEMAP);
		ResponseEntity<?> response = controller.GenerateSiteMapUsingFileName("sitemap.xml");
		Sitemap body = (Sitemap) response.getBody();
		assertThat(body,equalTo(SITEMAP));
	}
	
	@Test (expected = NotFoundException.class)
	public void throwsExceptionWhenWrongFileNameRequested() throws NotFoundException{
		controller.GenerateSiteMapUsingFileName("blah.xml");
	}
}
