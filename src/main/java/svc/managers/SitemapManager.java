package svc.managers;

import org.springframework.stereotype.Component;
import svc.models.Sitemap;
import svc.models.SitemapUrl;

@Component
public class SitemapManager {
	
	public Sitemap generateSitemap() {
		Sitemap sitemap = new Sitemap();
		
		SitemapUrl main = new SitemapUrl("http://www.yourstlcourts.com/");
		sitemap.addUrl(main);
		
		SitemapUrl info = new SitemapUrl("http://www.yourstlcourts.com/info");
		sitemap.addUrl(info);
		
		SitemapUrl goingToCourt = new SitemapUrl("http://www.yourstlcourts.com/goingToCourt");
		sitemap.addUrl(goingToCourt);
		
		SitemapUrl communityService = new SitemapUrl("http://www.yourstlcourts.com/communityService");
		sitemap.addUrl(communityService);
		
		SitemapUrl help = new SitemapUrl("http://www.yourstlcourts.com/help");
		sitemap.addUrl(help);
		
		SitemapUrl smsInstructions = new SitemapUrl("http://www.yourstlcourts.com/smsInstructions");
		sitemap.addUrl(smsInstructions);
		
		SitemapUrl about = new SitemapUrl("http://www.yourstlcourts.com/about");
		sitemap.addUrl(about);
		
		return sitemap;
	}
}