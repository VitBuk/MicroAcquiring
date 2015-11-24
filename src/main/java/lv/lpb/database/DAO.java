package lv.lpb.database;

import java.util.List;

public interface DAO<T> {
    public T create(T t);
    public T update(T t);    
    public T get(Long id);
    public List<T> getAll();
}
