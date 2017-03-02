package svc.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HashUtil{
	@Value("${stlcourts.security.municipalitySalt}")
	String municipalitySalt;
	@Value("${stlcourts.security.judgeSalt}")
	String judgeSalt;
	@Value("${stlcourts.security.courtSalt}")
	String courtSalt;
	
	public Hashids municipalityHashids;
	public Hashids judgeHashids;
	public Hashids courtHashids;
	
	private void initializeSalts(){
		if (municipalityHashids == null){
			municipalityHashids = new Hashids(municipalitySalt,8);
			judgeHashids = new Hashids(judgeSalt,8);
			courtHashids = new Hashids(courtSalt,8);
		}
	}
	
	public String encode(Class<?> fieldClass,String longStringValue){
		initializeSalts();
		long longValue = Long.parseLong(longStringValue);
		return this.encode(fieldClass,longValue);
	}
	
	public String encode(Class<?> fieldClass, long value){
		initializeSalts();
		String hashedValue = "";
		switch (fieldClass.getTypeName()){
			case "svc.models.Municipality":
				hashedValue = municipalityHashids.encode(value);
				break;
			case "svc.models.Judge":
				hashedValue = judgeHashids.encode(value);
				break;
			case "svc.models.Court":
				hashedValue = courtHashids.encode(value);
				break;
		}
		return hashedValue;
	}
	
	public long decode(Class<?> fieldClass, String hashValue){
		long idValue = 0L;
		switch (fieldClass.getTypeName()){
			case "svc.models.Municipality":
				idValue = municipalityHashids.decode(hashValue)[0];
				break;
			case "svc.models.Judge":
				idValue = judgeHashids.decode(hashValue)[0];
				break;
			case "svc.models.Court":
				idValue = courtHashids.decode(hashValue)[0];
				break;
		}
		return idValue;
	}
}
