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
public class MerchantBean implements Bean<Merchant> {
    
    @PersistenceContext(unitName = "MySql")
    EntityManager entityManager;
    
    @Override
    public void persist(Merchant merchant) {
        entityManager.persist(merchant);
    }
    
    @Override
    public Merchant find(Long id) {
        return entityManager.find(Merchant.class, id);
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public void update (Merchant merchant) {
        Merchant merchantFromDB = find(merchant.getId());
        entityManager.lock(merchantFromDB, LockModeType.OPTIMISTIC);
        merchantFromDB.setStatus(merchant.getStatus());
        entityManager.merge(merchantFromDB);
        entityManager.flush();
    }
    
    @Override
    public void delete (Long id) {
        entityManager.remove(find(id));
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public List<Merchant> getAll() {
        return entityManager.createNamedQuery("Merchant.findAll", Merchant.class).getResultList();
    }
}
