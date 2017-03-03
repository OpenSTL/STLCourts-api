package svc.types;

public class HashableEntity<CLASS>{
	private Class<CLASS> type;
	
	private Long value;
	
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
