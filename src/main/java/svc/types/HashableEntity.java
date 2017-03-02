package svc.types;

public class HashableEntity<CLASS>{
	public Class<CLASS> type;
	
	public Long value;
	
	public HashableEntity(Class<CLASS> cls, Long id){
		this.value = id;
		this.type = cls;
	}
	
	public Long getValue(){
		return value;
	}
	
	public void setValue(Long longValue){
		value = longValue;
	}
	
	public Class<CLASS> getType(){
		return type;
	}
}
