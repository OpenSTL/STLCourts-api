package svc.managers;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import svc.data.municipal.CourtDAO;
import svc.models.Court;
import svc.models.Sitemap;
import svc.models.SitemapUrl;
import svc.security.HashUtil;

@Component
public class SitemapManager {
	@Value("${stlcourts.clientURL}")
	String clientURL;

	@Inject
	CourtDAO courtDAO;
	
	@Inject
	HashUtil hashUtil;
	
	private static final List<String> staticUris = Arrays.asList("/", "/goingToCourt", "/communityService", "/help", "/smsInstructions", "/about", "/legal");

	public Sitemap generate() {
	  Sitemap sitemap = new Sitemap();
	  for(String uri : staticUris) {
	    sitemap.addUrl(new SitemapUrl(clientURL + uri));
	  }
	
	  List<Court> courts = courtDAO.getAllCourts();
	  for(Court court : courts){
		SitemapUrl courtUrl = new SitemapUrl(clientURL+"/court/"+hashUtil.encode(court.id.getType(), court.id.getValue()));
		sitemap.addUrl(courtUrl);
	  }
		
	  return sitemap;
	}
}