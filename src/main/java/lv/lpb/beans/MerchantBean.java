package lv.lpb.beans;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lv.lpb.domain.Merchant;

@Stateless
public class MerchantBean {
    
    @PersistenceContext(unitName = "MySql")
    EntityManager entityManager;
    
    public void persist(Merchant merchant) {
        entityManager.persist(merchant);
    }
    
    public List<Merchant> getAll() {
        return entityManager.createNamedQuery("Merchant.findAll", Merchant.class).getResultList();
    }
}
