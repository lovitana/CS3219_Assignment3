package sg.cs3219.dataModel;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class RefPaper extends DataModel {
	
	public static final List<String> ATT = Collections.unmodifiableList(Arrays.asList(
			"booktitle","author","title","date","editor","affiliation","note","pages","publisher",
			"volume","issue","number","marker","tech"
			));
	
	private final Map<String,String> map;
	
	public RefPaper(Map<String,String> map){
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
