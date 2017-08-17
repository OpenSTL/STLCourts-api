package svc.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement (localName="url")
public class SitemapUrl{
	@JacksonXmlProperty
	private String loc;
	
	public SitemapUrl(String loc){
		super();
		this.loc = loc;
	}
	
	public String getLoc(){
		return this.loc;
	}
	
	public void setLoc(String loc){
		this.loc = loc;
	}
	
}
