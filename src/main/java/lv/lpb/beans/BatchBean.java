package lv.lpb.beans;

import java.util.List;
import lv.lpb.domain.Batch;

public interface BatchBean extends Bean<Batch> {

    @Override
    public void persist(Batch batch);

    @Override
    public Batch find(Long id);

    @Override
    public void update(Batch batch);

    @Override
    public void delete(Long id);

    @Override
    public List<Batch> getAll();
}
