package lv.lpb.database.DAOImpl;

import java.util.List;
import java.util.Map;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import lv.lpb.database.DAOQualifier;
import lv.lpb.database.TransactionDAO;
import lv.lpb.domain.Transaction;

@Singleton
@DAOQualifier(daoType = DAOQualifier.DaoType.DATABASE)
public class TransactionDAOImpl implements TransactionDAO {

    @PersistenceContext(unitName = "MySql")
    EntityManager entityManager;

    @Override
    public Transaction create(Transaction transaction) {
        entityManager.persist(transaction);
        return transaction;
    }

    @Override
    public Transaction get(Long id) {
        return entityManager.find(Transaction.class, id);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public Transaction update(Transaction transaction) {
        Transaction transactionFromDB = get(transaction.getId());
        entityManager.lock(transactionFromDB, LockModeType.OPTIMISTIC);
        transactionFromDB.setAmount(transaction.getAmount());
        transactionFromDB.setBatchId(transaction.getBatchId());
        transactionFromDB.setCreated(transaction.getCreated());
        transactionFromDB.setCurrency(transaction.getCurrency());
        transactionFromDB.setStatus(transaction.getStatus());
        transactionFromDB.setUpdated(transaction.getUpdated());

        entityManager.merge(transactionFromDB);
        entityManager.flush();

        return transaction;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public List<Transaction> getAll() {
        return entityManager.createNamedQuery("Tranasaction.findAll", Transaction.class).getResultList();
    }

    @Override
    public List<Transaction> getByMerchantId(Long merchantId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Transaction> getByParams(Map<String, Object> filterParams, Map<String, Object> pageParams) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Transaction> lastDayTransactions() {
        return entityManager.createNativeQuery("SELECT * FROM Transaction WHERE created "
                + "= CURDATE() - INTERVAL 1 DAY").getResultList();
    }
}
