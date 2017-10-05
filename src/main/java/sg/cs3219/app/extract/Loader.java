package sg.cs3219.app.extract;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface Loader<T> {
	
	public T loadFile(String src);
	
	public default List<T> loadMultiple(Collection<String> srcs){
		List<T> files = new ArrayList<T>();
		for(String s:srcs){
			files.add(loadFile(s));
		}
		return files;
	}
	
}
