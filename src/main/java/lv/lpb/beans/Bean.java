package lv.lpb.beans;

import java.util.List;

public interface Bean<T> {

    public void persist(T t);

    public T find(Long id);

    public void update(T t);

    public void delete(Long id);

    public List<T> getAll();
}
