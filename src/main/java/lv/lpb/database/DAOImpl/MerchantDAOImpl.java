package lv.lpb.database.DAOImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import lv.lpb.database.DAOQualifier;
import lv.lpb.database.MerchantDAO;
import lv.lpb.domain.Merchant;
import lv.lpb.rest.params.PageParams;

@Singleton
@DAOQualifier(daoType = DAOQualifier.DaoType.DATABASE)
public class MerchantDAOImpl implements MerchantDAO {

    @PersistenceContext(unitName = "MySql")
    EntityManager entityManager;

    @Override
    public Merchant create(Merchant merchant) {
        entityManager.persist(merchant);
        return merchant;
    }

    @Override
    public Merchant get(Long id) {
        return entityManager.find(Merchant.class, id);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public Merchant update(Merchant merchant) {
        Merchant merchantFromDB = get(merchant.getId());
        entityManager.lock(merchantFromDB, LockModeType.OPTIMISTIC);
        merchantFromDB.setStatus(merchant.getStatus());
        entityManager.merge(merchantFromDB);
        entityManager.flush();

        return merchant;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public List<Merchant> getAll() {
        return entityManager.createNamedQuery("Merchant.findAll", Merchant.class).getResultList();
    }


}
