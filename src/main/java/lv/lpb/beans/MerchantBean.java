package lv.lpb.beans;

import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import lv.lpb.domain.Merchant;

@Stateless
public class MerchantBean {
    
    @PersistenceContext(unitName = "MySql")
    EntityManager entityManager;
    
    public void persist(Merchant merchant) {
        entityManager.persist(merchant);
    }
    
    public Merchant find(Long id) {
        return entityManager.find(Merchant.class, id);
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void update (Long id, Merchant.Status status) {
        Merchant merchant = find(id);
        entityManager.lock(merchant, LockModeType.OPTIMISTIC);
        merchant.setStatus(status);
        entityManager.merge(merchant);
        entityManager.flush();
    }
    
    public void delete (Long id) {
        entityManager.remove(find(id));
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Merchant> getAll() {
        return entityManager.createNamedQuery("Merchant.findAll", Merchant.class).getResultList();
    }
    
    
}
