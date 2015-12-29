package lv.lpb.database;

import java.util.List;

public interface GenericDAO<T> {

    public T create(T t);

    public T get(Long id);

    public T update(T t);

    public List<T> getAll();
}
