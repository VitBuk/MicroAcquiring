package lv.lpb.database.DAOImpl;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Lock;
import static javax.ejb.LockType.READ;
import static javax.ejb.LockType.WRITE;
import javax.ejb.Singleton;
import lv.lpb.database.BatchCollectionDAO;
import lv.lpb.domain.Batch;

@Singleton
@Lock(READ)
public class BatchMockEJB implements BatchCollectionDAO {
    private List<Batch> batches = new ArrayList<>();

    @Override
    @Lock(WRITE)
    public Batch create(Batch batch) {
        batches.add(batch);
        return batch;
    }

    @Override
    @Lock(WRITE)
    public Batch update(Batch batch) {
        batches.remove(get(batch.getId()));
        batches.add(batch);
        return batch;
    }

    @Override
    public Batch get(Long id) {
        for (Batch batch : batches) {
            if (id.equals(batch.getId())) {
                return batch;
            }
        }
        
        return null;
    }

    @Override
    public List<Batch> getAll() {
        return batches;
    }
    
    
}
