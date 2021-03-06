package lv.lpb.database.DAOMockImpl;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Lock;
import static javax.ejb.LockType.READ;
import static javax.ejb.LockType.WRITE;
import javax.ejb.Stateless;
import lv.lpb.database.BatchDAO;
import lv.lpb.domain.Batch;
import lv.lpb.Constants;
import lv.lpb.database.DAO;
import lv.lpb.database.DAO.Type;

@Stateless
@Lock(READ)
@DAO(Type.COLLECTION)
public class BatchDAOMockImpl implements BatchDAO {

    private List<Batch> batches = new ArrayList<>();

    @Override
    @Lock(WRITE)
    public Batch create(Batch batch) {
        // id added by "DB"
        batch.setId(Constants.TEST_MERCHANT_ID);
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
