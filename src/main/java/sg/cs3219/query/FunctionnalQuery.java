package sg.cs3219.query;

import java.util.List;

import sg.cs3219.dataModel.DataModel;

public final class FunctionnalQuery<T> implements Query<T>{
	
	private final T zero;
	private final boolean base;
	private final Criteria crit;
	private final Combiner<T> comb;
	private final Output<T> out;
	
	public FunctionnalQuery(T zero,boolean base,Criteria crit,Combiner<T> comb,Output<T> out){
		this.zero = zero;
		this.base = base;
		this.crit = crit;
		this.comb = comb;
		this.out = out;
	}


	@Override
	public T zero() {
		return zero;
	}

	@Override
	public boolean onlybase() {
		return base;
	}
	
	@Override
	public boolean criteria(List<DataModel> data) {
		return crit.evaluate(data);
	}

	@Override
	public T combine(List<DataModel> data, T oldResult) {
		return comb.combine(data, oldResult);
	}

	@Override
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
