package lv.lpb.database.DAOImpl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lv.lpb.database.DAO;
import lv.lpb.database.TransactionDAO;
import lv.lpb.domain.Currency;
import lv.lpb.domain.Merchant;
import lv.lpb.domain.Transaction;
import lv.lpb.rest.params.PageParams;
import lv.lpb.rest.params.TransactionFilterParams;

@Stateless
@DAO
public class TransactionDAOImpl implements TransactionDAO {

    @PersistenceContext(unitName = "MySql")
    EntityManager entityManager;

    @Override
    public Transaction create(Transaction transaction) {
        entityManager.persist(transaction);
        return transaction;
    }

    @Override
    public Transaction get(Long id) {
        return entityManager.find(Transaction.class, id);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public Transaction update(Transaction transaction) {
        entityManager.merge(transaction);
        return transaction;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public List<Transaction> getAll() {
        return entityManager.createNamedQuery("Transaction.findAll", Transaction.class).getResultList();
    }

    @Override
    public List<Transaction> getByMerchant(Merchant merchant) {
        Query query = entityManager.createQuery("SELECT t FROM Transaction t WHERE t.merchant = :merchant");
        query.setParameter("merchant", merchant);

        return query.getResultList();
    }

    @Override
    public List<Transaction> getByParams(Map<String, Object> filterParams, Map<String, Object> pageParams) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Transaction> criteriaQuery = criteriaBuilder.createQuery(Transaction.class);
        Root<Transaction> transaction = criteriaQuery.from(Transaction.class);
        criteriaQuery.select(transaction);
        criteriaQuery.orderBy(sort(criteriaBuilder, transaction, pageParams));

        List<Predicate> criteria = new ArrayList<Predicate>();
        if (filterParams.get(TransactionFilterParams.MERCHANT) != null) {
            ParameterExpression<Merchant> p = criteriaBuilder.parameter(Merchant.class, TransactionFilterParams.MERCHANT);
            criteria.add(criteriaBuilder.equal(transaction.get("merchant"), p));
        }

        if (filterParams.get(TransactionFilterParams.CURRENCY) != null) {
            ParameterExpression<Currency> p = criteriaBuilder.parameter(Currency.class, TransactionFilterParams.CURRENCY);
            criteria.add(criteriaBuilder.equal(transaction.get("currency"), p));
        }

        if (filterParams.get(TransactionFilterParams.STATUS) != null) {
            ParameterExpression<Transaction.Status> p = criteriaBuilder.parameter(Transaction.Status.class, TransactionFilterParams.STATUS);
            criteria.add(criteriaBuilder.equal(transaction.get("status"), p));
        }

        if (filterParams.get(TransactionFilterParams.CREATED) != null) {
            ParameterExpression<LocalDateTime> p = criteriaBuilder.parameter(LocalDateTime.class, TransactionFilterParams.CREATED);
            criteria.add(criteriaBuilder.equal(transaction.get("created"), p));
        }

        if (criteria.size() == 0) {
            return getAll();
        } else if (criteria.size() == 1) {
            criteriaQuery.where(criteria.get(0));
        } else {
            criteriaQuery.where(criteriaBuilder.and(criteria.toArray(new Predicate[0])));
        }

        TypedQuery<Transaction> typedQuery = entityManager.createQuery(criteriaQuery);
        if (filterParams.get(TransactionFilterParams.MERCHANT) != null) {
            typedQuery.setParameter(TransactionFilterParams.MERCHANT,
                    filterParams.get(TransactionFilterParams.MERCHANT));
        }
        if (filterParams.get(TransactionFilterParams.CURRENCY) != null) {
            typedQuery.setParameter(TransactionFilterParams.CURRENCY,
                    filterParams.get(TransactionFilterParams.CURRENCY));
        }
        if (filterParams.get(TransactionFilterParams.STATUS) != null) {
            typedQuery.setParameter(TransactionFilterParams.STATUS,
                    filterParams.get(TransactionFilterParams.STATUS));
        }
        if (filterParams.get(TransactionFilterParams.CREATED) != null) {
            typedQuery.setParameter(TransactionFilterParams.CREATED,
                    filterParams.get(TransactionFilterParams.CREATED));
        }

        if ((pageParams.get(PageParams.OFFSET) != null && (pageParams.get(PageParams.LIMIT) != null))) {
            typedQuery.setFirstResult((Integer) pageParams.get(PageParams.OFFSET));
            typedQuery.setMaxResults((Integer) pageParams.get(PageParams.LIMIT));
        }

        return typedQuery.getResultList();
    }

    @Override
    public List<Transaction> lastDayTransactions() {
        return entityManager.createNativeQuery("SELECT * FROM Transaction WHERE created "
                + "= CURDATE() - INTERVAL 1 DAY").getResultList();
    }

    @Override
    public Map<Currency, BigDecimal> totalDayAmount() {
        Map<Currency, BigDecimal> totalAmount = new HashMap<>();
        for (Currency currency : Currency.values()) {
            Query query = entityManager.createQuery("SELECT t SUM(amount) FROM"
                    + " Transaction t WHERE t.created = CURDATE() AND t.currency = :currency");
            query.setParameter("currency", currency);
            BigDecimal sum = (BigDecimal) query.getSingleResult();
            totalAmount.put(currency, sum);
        }

        return totalAmount;
    }

    private List<Order> sort(CriteriaBuilder criteriaBuilder, Root<Transaction> transaction, Map<String, Object> pageParams) {
        List<Order> orderList = new ArrayList<>();
        if ("id".equals(pageParams.get(PageParams.SORT))) {
            if ("reverse".equals(pageParams.get(PageParams.ORDER))) {
                orderList.add(criteriaBuilder.desc(transaction.get("id")));
            } else {
                orderList.add(criteriaBuilder.asc(transaction.get("id")));
            }
        }

        if ("created".equals(pageParams.get(PageParams.SORT))) {
            if ("reverse".equals(pageParams.get(PageParams.ORDER))) {
                orderList.add(criteriaBuilder.desc(transaction.get("created")));
            } else {
                orderList.add(criteriaBuilder.asc(transaction.get("created")));
            }
        }

        if (orderList.isEmpty()) {
            orderList.add(criteriaBuilder.asc(transaction.get("id")));
        }

        return orderList;
    }
}
