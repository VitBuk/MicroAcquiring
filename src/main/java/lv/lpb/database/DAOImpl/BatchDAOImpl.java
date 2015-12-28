package lv.lpb.database.DAOImpl;

import java.util.List;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import lv.lpb.database.BatchDAO;
import lv.lpb.database.DAOQualifier;
import lv.lpb.domain.Batch;

@Singleton
@DAOQualifier(daoType = DAOQualifier.DaoType.DATABASE)
public class BatchDAOImpl implements BatchDAO {

    @PersistenceContext(unitName = "MySql")
    EntityManager entityManager;

    @Override
    public Batch create(Batch batch) {
        entityManager.persist(batch);
        return batch;
    }

    @Override
    public Batch get(Long id) {
        return entityManager.find(Batch.class, id);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public Batch update(Batch batch) {
        //Batch batchFromDB = get(batch.getId());
        //entityManager.lock(batchFromDB, LockModeType.OPTIMISTIC);
        //batchFromDB.setTransactions(batch.getTransactions());
        entityManager.merge(batch);       
        //entityManager.flush();
        
        return batch;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public List<Batch> getAll() {
        return entityManager.createNamedQuery("Batch.findAll", Batch.class).getResultList();
    }

}
