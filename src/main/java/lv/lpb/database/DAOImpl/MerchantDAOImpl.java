package lv.lpb.database.DAOImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import lv.lpb.database.DAO;
import lv.lpb.database.MerchantDAO;
import lv.lpb.domain.Merchant;
import lv.lpb.rest.params.MerchantFilterParams;
import lv.lpb.rest.params.PageParams;

@Stateless
@DAO
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
        entityManager.merge(merchant);
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

        if (filterParams.get(MerchantFilterParams.ID) != null && filterParams.get(MerchantFilterParams.STATUS) == null) {
            merchantsByParams.add(get(Long.parseLong(filterParams.get(MerchantFilterParams.ID).toString())));
            return merchantsByParams;
        }

        query = entityManager.createQuery(queryString(filterParams, pageParams));

        if (filterParams.get(MerchantFilterParams.STATUS) != null) {
            query.setParameter("status", filterParams.get(MerchantFilterParams.STATUS));
        }

        if (pageParams.get(PageParams.OFFSET) != null && pageParams.get(PageParams.LIMIT) != null) {
            query.setFirstResult((Integer) pageParams.get(PageParams.OFFSET));
            query.setMaxResults((Integer) pageParams.get(PageParams.LIMIT));
        }

        return query.getResultList();
    }

    private String queryString(Map<String, Object> filterParams, Map<String, Object> pageParams) {
        String queryString = "SELECT m FROM Merchant m";
        if (filterParams.get(MerchantFilterParams.STATUS) != null) {
            queryString += " WHERE m.status = :status";
        }

        queryString += sort(String.valueOf(pageParams.get(PageParams.SORT)),
                String.valueOf(pageParams.get(PageParams.ORDER)));

        return queryString;
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
