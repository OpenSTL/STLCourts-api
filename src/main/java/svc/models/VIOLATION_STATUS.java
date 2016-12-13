package svc.models;

public enum VIOLATION_STATUS{
	CONT_FOR_PAYMENT ("Continued for payment"),
	FTA_WARRANT_ISSUED ("An arrest warrent has been issued for failure to appear to your court date"),
	DISMISS_WITHOUT_COSTS ("Dismissed"),
	CLOSED ("Closed");
	
	private final String msg;
	
	VIOLATION_STATUS (String msg){
		this.msg = msg;
	}
	
	@Override
	public String toString(){
		return this.msg;
	}
	
	public static VIOLATION_STATUS convertDatabaseStatusToEnum(String statusFromDatabase){
		return VIOLATION_STATUS.valueOf(statusFromDatabase.replaceAll(" ", "_"));
	}
}
