package lv.lpb.database;

import java.util.List;
import lv.lpb.domain.Batch;

public interface BatchCollectionDAO extends DAO<Batch>{
    
    @Override
    public Batch create(Batch batch);
    
    @Override
    public Batch update(Batch batch); 
    
    @Override
    public Batch get(Long id);
    
    @Override
    public List<Batch> getAll();
}
