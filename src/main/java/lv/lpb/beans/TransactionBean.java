package lv.lpb.beans;

import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import lv.lpb.domain.Transaction;

@Stateless
public class TransactionBean implements Bean<Transaction> {
    @PersistenceContext(unitName = "MySql")
    EntityManager entityManager;
    
    @Override
    public void persist(Transaction transaction) {
        entityManager.persist(transaction);
    }
    
    @Override
     public Transaction find(Long id) {
        return entityManager.find(Transaction.class, id);
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public void update (Transaction transaction) {
        Transaction transactionFromDB = find(transaction.getId());
        entityManager.lock(transactionFromDB, LockModeType.OPTIMISTIC);
        transactionFromDB.setAmount(transaction.getAmount());
        transactionFromDB.setBatchId(transaction.getBatchId());
        transactionFromDB.setCreated(transaction.getCreated());
        transactionFromDB.setCurrency(transaction.getCurrency());
        transactionFromDB.setStatus(transaction.getStatus());
        transactionFromDB.setUpdated(transaction.getUpdated());
        
        entityManager.merge(transactionFromDB);
        entityManager.flush();
    }
    
    @Override
    public void delete (Long id) {
        entityManager.remove(find(id));
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public List<Transaction> getAll() {
        return entityManager.createNamedQuery("Tranasaction.findAll", Transaction.class).getResultList();
    }
}
