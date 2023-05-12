package data_access;

import java.util.Collection;

public interface IDataAccessor<T, U> {

	Collection<U> getAll();
	
	U getById(T id);

	U update(U user);
	
	U add(U user);
	
	U delete(U user);
	
	boolean existsById(T id);
	
	boolean existsAllByIds(Collection<T> ids);
}
