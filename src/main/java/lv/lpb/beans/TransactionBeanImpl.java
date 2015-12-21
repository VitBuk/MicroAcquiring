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
public class TransactionBeanImpl implements TransactionBean {

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
    public void update(Transaction transaction) {
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
    public void delete(Long id) {
        entityManager.remove(find(id));
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public List<Transaction> getAll() {
        return entityManager.createNamedQuery("Tranasaction.findAll", Transaction.class).getResultList();
    }
    
    //GQL
//    public List<Transaction> lastDayTransactions() {
//        List<Transaction> transactions = new ArrayList<>();
//        
//        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Transaction> criteriaQuery = criteriaBuilder.createQuery(Transaction.class);
//        Root<Transaction> transaction = criteriaQuery.from(Transaction.class);
//        criteriaQuery.select(transaction).where(criteriaBuilder.equal(transaction.get("created"), LocalDate.now().minusDays(1L)));
//      
//    }
    
//    public List<Transaction> getByParams(Map<String, Object> filterParams, Map<String, Object> pageParams) {
//        use Criteria
//    }

    @Override
    public List<Transaction> lastDayTransactions() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
