package svc.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement (localName="urlset", namespace="http://www.sitemaps.org/schemas/sitemap/0.9")
public class Sitemap{
	
	@JacksonXmlProperty(localName = "url")
	@JacksonXmlElementWrapper(useWrapping = false)
	private List<SitemapUrl> urls = new ArrayList<SitemapUrl>();
	
	public List<SitemapUrl> getUrls(){
		return this.urls;
	}
	
	public void setUrls(List<SitemapUrl> urls){
		this.urls = urls;
	}
	
	public void addUrl(SitemapUrl url){
		this.urls.add(url);
	}
	
}
