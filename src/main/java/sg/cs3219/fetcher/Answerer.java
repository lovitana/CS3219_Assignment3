package sg.cs3219.fetcher;

import sg.cs3219.query.Query;

public interface Answerer {
	
	public <T>String answer(Query<T> q);
}
