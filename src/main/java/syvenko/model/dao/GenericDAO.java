package syvenko.model.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDAO <T extends Serializable> {
    public T findOne( long id );
    public List< T > findAll();
    public void create( T entity );
    public T update( T entity );
    public void delete( T entity );
    public void deleteById( long entityId );
}
