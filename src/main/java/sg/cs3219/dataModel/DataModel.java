package sg.cs3219.dataModel;

import java.util.List;
import java.util.Map;

public abstract class  DataModel {

	public abstract List<String> Attributes();
	public abstract Map<String,String> mapAttributes();
	
	public final String getAttribute(String name){
		if(Attributes().contains(name)){
			return mapAttributes().get(name);
		}else{
			throw new IllegalArgumentException("the attribute "+name+" is not a member of this data");
		}
	}

}
