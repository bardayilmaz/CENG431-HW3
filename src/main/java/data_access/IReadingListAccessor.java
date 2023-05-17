package data_access;

import java.util.List;

import model.ReadingListModel;

public interface IReadingListAccessor extends IDataAccessor<String, ReadingListModel>{

	ReadingListModel getByName(String name);
	
	List<ReadingListModel> getAllByCreatorName(String name);
	
	boolean existByName(String name);
	
}
