package data_access;

import java.util.Collection;

public interface IDataAccessor<T, U> {

	Collection<U> getAll();
	
	U getById(T id);

	U update(U data);
	
	U add(U data);
	
	U delete(T id);
	
	boolean existsById(T id);
	
	boolean existsAllByIds(Collection<T> ids);
}
