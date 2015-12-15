package lv.lpb.beans;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lv.lpb.domain.Batch;

@Stateless
public class BatchBean {
    @PersistenceContext(unitName = "MySql")
    EntityManager entityManager;
    
    public void persist(Batch batch) {
        entityManager.persist(batch);
    }
    
    public List<Batch> getAll() {
        return entityManager.createNamedQuery("Batch.findAll", Batch.class).getResultList();
    }
}
