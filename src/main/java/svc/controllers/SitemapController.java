package svc.controllers;

import javax.inject.Inject;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import svc.managers.SitemapManager;
import svc.models.Sitemap;


@RestController
@EnableAutoConfiguration
public class SitemapController {
	@Inject
	SitemapManager sitemapManager;
	
	@ResponseBody
	@RequestMapping(
			method = RequestMethod.GET,
			value = "/sitemap/{file:.+}",
			produces = MediaType.APPLICATION_XML_VALUE,
			headers = "Accept=application/xml")
	public ResponseEntity<?> GenerateSiteMapUsingFileName(@PathVariable("file") String fileName) throws NotFoundException {
		if (!fileName.equals("sitemap.xml")) {
			throw new NotFoundException("Wrong XML File Found");
		}
		
		Sitemap sitemap = sitemapManager.generate();
		
		return ResponseEntity.ok(sitemap);
    }
	
	@ResponseBody
	@RequestMapping(
			method = RequestMethod.GET,
			value = "/sitemap",
			produces = MediaType.APPLICATION_XML_VALUE,
			headers = "Accept=application/xml")
	public ResponseEntity<?> GenerateSiteMap(){
		Sitemap sitemap = sitemapManager.generate();
		
		return ResponseEntity.ok(sitemap);
    }
}