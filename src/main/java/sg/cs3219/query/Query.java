package sg.cs3219.query;

import java.util.List;

import sg.cs3219.dataModel.DataModel;

public interface Query<T> {

	public abstract boolean criteria(List<DataModel> data);
	public abstract T combine(List<DataModel> data, T oldResult);
	public abstract String output(T finalResult);

}
