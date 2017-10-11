package svc.controllers;

import javax.inject.Inject;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import svc.managers.SitemapManager;
import svc.models.Sitemap;

//method only returns data in xml format if ".xml" extension is used.  example:  yourstlcourts.com/sitemap.xml

@RestController
@EnableAutoConfiguration
public class SitemapController {
	@Inject
	SitemapManager sitemapManager;
	
	@ResponseBody
	@RequestMapping(
			method = RequestMethod.GET,
			value = "/sitemap")
	public ResponseEntity<?> GenerateSiteMap(){
		Sitemap sitemap = sitemapManager.generate();
		
		return ResponseEntity.ok(sitemap);
    }
}