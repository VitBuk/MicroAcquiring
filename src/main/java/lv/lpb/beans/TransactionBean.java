package lv.lpb.beans;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lv.lpb.domain.Transaction;

@Stateless
public class TransactionBean {
    @PersistenceContext(unitName = "MySql")
    EntityManager entityManager;
    
    public void persist(Transaction transaction) {
        entityManager.persist(transaction);
    }
    
    public List<Transaction> getAll() {
        return entityManager.createNamedQuery("Tranasaction.findAll", Transaction.class).getResultList();
    }
}
