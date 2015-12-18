package lv.lpb.beans;

import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import lv.lpb.domain.Batch;

@Stateless
public class BatchBeanImpl implements BatchBean {
    @PersistenceContext(unitName = "MySql")
    EntityManager entityManager;
    
    @Override
    public void persist(Batch batch) {
        entityManager.persist(batch);
    }
    
    @Override
    public Batch find(Long id) {
        return entityManager.find(Batch.class, id);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public void update(Batch batch) {
        Batch batchFromDB = find(batch.getId());
        entityManager.lock(batchFromDB, LockModeType.OPTIMISTIC);
        batchFromDB.setTransactions(batch.getTransactions());
        entityManager.merge(batchFromDB);
        entityManager.flush();
    }

    @Override
    public void delete(Long id) {
        entityManager.remove(find(id));
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public List<Batch> getAll() {
        return entityManager.createNamedQuery("Batch.findAll", Batch.class).getResultList();
    }
}
