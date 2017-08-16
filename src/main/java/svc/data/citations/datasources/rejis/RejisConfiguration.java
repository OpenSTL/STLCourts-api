package svc.data.citations.datasources.rejis;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RejisConfiguration {

	@Value("${stlcourts.citationDataSources.rejis.rootUrl}")
	private String rootUrl;
	
	public final String apiKey;
	
	public RejisConfiguration(@Value("${stlcourts.citationDataSources.rejis.username}") String username,
							  @Value("${stlcourts.citationDataSources.rejis.password}") String password){
		String auth = username + ":" + password;
		byte[] encodedAuth = Base64.encodeBase64(auth.getBytes());
		apiKey = "Basic " + new String( encodedAuth );
	}
	
	public String getRootUrl(){
		return this.rootUrl;
	}
}
