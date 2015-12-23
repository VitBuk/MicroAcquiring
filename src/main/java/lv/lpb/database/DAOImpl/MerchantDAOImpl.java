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
import lv.lpb.rest.params.MerchantFilterParams;
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

    @Override
    public List<Merchant> getByParams(Map<String, Object> filterParams, Map<String, Object> pageParams) {
        List<Merchant> merchantsByParams = new ArrayList<>();
        Query query;
        boolean hasStatus = false;
        String queryString = "SELECT m";

        if (filterParams.get(MerchantFilterParams.ID) != null) {
            merchantsByParams.add(get(Long.parseLong(filterParams.get(MerchantFilterParams.ID) + "")));
            return merchantsByParams;
        } else if (filterParams.get(MerchantFilterParams.STATUS) == null) {
            queryString += " FROM Merchant m";
        } else {
            queryString += " FROM Merchant m WHERE m.status = :status";
            hasStatus = true;
        }

        queryString += sort(pageParams.get(PageParams.SORT) + "",
                pageParams.get(PageParams.ORDER) + "");

        query = entityManager.createQuery(queryString);
        if (hasStatus) {
            query.setParameter("status", filterParams.get(MerchantFilterParams.STATUS));
        }

        if ((pageParams.get(PageParams.OFFSET) instanceof java.lang.Object) == true
                && (pageParams.get(PageParams.LIMIT) instanceof java.lang.Object) == true) {
            query.setFirstResult((Integer) pageParams.get(PageParams.OFFSET));
            query.setMaxResults((Integer) pageParams.get(PageParams.LIMIT));
        }

        return query.getResultList();
    }

    private String sort(String sortParam, String order) {
        String queryString = "";
        if ("id".equals(sortParam)) {
            queryString += " ORDER BY id";
        } else if ("created".equals(sortParam)) {
            queryString += " ORDER BY created";
        }

        if ("reverse".equals(order)) {
            queryString += " DESC";
        }

        return queryString;
    }
}
