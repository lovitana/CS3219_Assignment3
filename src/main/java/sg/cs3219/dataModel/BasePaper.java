package sg.cs3219.dataModel;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class BasePaper extends DataModel {
	
	public static final List<String> ATT = Collections.unmodifiableList(Arrays.asList(
			"title","author","affiliation","address","email"
			));
	
	private final Map<String,String> map;
	
	public BasePaper(Map<String,String> map){
		this.map = map ;
	}

	@Override
	public List<String> Attributes() {
		return ATT;
	}

	@Override
	public Map<String, String> mapAttributes() {
		return map;
	}
	
	
}
