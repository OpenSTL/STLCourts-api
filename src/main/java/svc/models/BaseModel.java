package svc.models;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

abstract class BaseModel {
	protected abstract String getTableName();
	
	public List<String> getNonListFieldNames(){
		Field[] fields = this.getClass().getDeclaredFields();
		List<String> sqlNames = new ArrayList<String>();
		for(Field f: fields){
			if (!f.getType().toString().contains("List"))
				sqlNames.add(this.getTableName()+"."+f.getName().toString());
		}
		return sqlNames;
	}
}
