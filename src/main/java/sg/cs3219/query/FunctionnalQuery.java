package sg.cs3219.query;

import java.util.List;

import sg.cs3219.dataModel.DataModel;

public class FunctionnalQuery<T> implements Query<T>{
	
	private final Criteria crit;
	private final Combiner<T> comb;
	private final Output<T> out;
	
	public FunctionnalQuery(Criteria crit,Combiner<T> comb,Output<T> out){
		this.crit = crit;
		this.comb = comb;
		this.out = out;
	}

	public boolean criteria(List<DataModel> data) {
		return crit.evaluate(data);
	}

	public T combine(List<DataModel> data, T oldResult) {
		return comb.combine(data, oldResult);
	}

	public String output(T finalResult) {
		return out.out(finalResult);
	}

	
	@FunctionalInterface
	public interface Criteria{
		
		public abstract boolean evaluate(List<DataModel> data);
		
	}
	
	@FunctionalInterface
	public interface Combiner<T>{
		public abstract T combine(List<DataModel> data,T oldRes);
		
	}
	
	@FunctionalInterface
	public interface Output<T>{
		
		public abstract String out(T result);
		
	}
}
