package lv.lpb.database.DAOImpl.mockEJB;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.Lock;
import static javax.ejb.LockType.READ;
import static javax.ejb.LockType.WRITE;
import javax.ejb.Singleton;
import lv.lpb.database.DAOQualifier;
import lv.lpb.database.DAOQualifier.DaoType;
import lv.lpb.database.TransactionDAO;
import lv.lpb.domain.Merchant;
import lv.lpb.domain.Transaction;
import lv.lpb.rest.params.PageParams;
import lv.lpb.rest.params.TransactionFilterParams;

@Singleton
@DAOQualifier(daoType = DaoType.COLLECTION)
@Lock(READ)
public class TransactionMockEJB implements TransactionDAO {

    private List<Transaction> transactions = new ArrayList<>();

    @PostConstruct
    public void init() {
        generateTransactions();
    }

    @Override
    @Lock(WRITE)
    public Transaction create(Transaction transaction) {
        transactions.add(transaction);
        return transaction;
    }

    @Override
    @Lock(WRITE)
    public Transaction update(Transaction transaction) {
        transactions.remove(get(transaction.getId()));
        transactions.add(transaction);
        return transaction;
    }

    @Override
    public Transaction get(Long id) {
        for (Transaction transaction : transactions) {
            if (id.equals(transaction.getId())) {
                return transaction;
            }
        }

        return null;
    }

    @Override
    public List<Transaction> getAll() {
        return transactions;
    }

    @Override
    public List<Transaction> getByParams(Map<String, Object> filterParams, Map<String, Object> pageParams) {
        List<Transaction> transactionsByParams = new ArrayList<>();

        transactionsByParams = filter(transactionsByParams, filterParams);
        transactionsByParams = sort(transactionsByParams, pageParams.get(PageParams.SORT) + "",
                pageParams.get(PageParams.ORDER) + "");

        if ((pageParams.get(PageParams.OFFSET) instanceof java.lang.Object) == true
                && (pageParams.get(PageParams.LIMIT) instanceof java.lang.Object) == true) {
            transactionsByParams = getByOffset(transactionsByParams, (Integer) pageParams.get(PageParams.OFFSET),
                    (Integer) pageParams.get(PageParams.LIMIT));
        }

        return transactionsByParams;
    }

    @Override
    public List<Transaction> getByMerchant(Merchant merchant) {
        List<Transaction> transactions = new ArrayList<>();
        for (Transaction transaction : getAll()) {
            if (merchant.equals(transaction.getMerchant())) {
                transactions.add(transaction);
            }
        }

        return transactions;
    }

    @Override
    public List<Transaction> lastDayTransactions() {
        List<Transaction> transactionsByDate = new ArrayList<>();
        for (Transaction transaction : getAll()) {
            if (transaction.getCreated().isEqual(LocalDateTime.now().minusDays(1L))) {
                transactionsByDate.add(transaction);
            }
        }

        return transactionsByDate;
    }

    private List<Transaction> filter(List<Transaction> transactionsByParams, Map<String, Object> filterParams) {
        for (Transaction transaction : getAll()) {
            if (filterParams.get(TransactionFilterParams.MERCHANT) == null || filterParams.get(TransactionFilterParams.MERCHANT).equals(transaction.getMerchant())) {
                if (filterParams.get(TransactionFilterParams.CURRENCY) == null || filterParams.get(TransactionFilterParams.CURRENCY).equals(transaction.getCurrency())) {
                    if (filterParams.get(TransactionFilterParams.STATUS) == null || filterParams.get(TransactionFilterParams.STATUS).equals(transaction.getStatus())) {
                        if (filterParams.get(TransactionFilterParams.CREATED) == null || filterParams.get(TransactionFilterParams.CREATED).equals(transaction.getCreated())) {
                            transactionsByParams.add(transaction);
                        }
                    }
                }
            }
        }

        return transactionsByParams;
    }

    private List<Transaction> sort(List<Transaction> transactionsByParams, String sortParam, String order) {
        if ("id".equals(sortParam)) {
            transactionsByParams.sort(Comparator.comparing(Transaction::getId));
            if ("reverse".equals(order)) {
                transactionsByParams.sort(Comparator.comparing(Transaction::getId).reversed());
            }
        }

        if ("initDate".equals(sortParam)) {
            transactionsByParams.sort(Comparator.comparing(Transaction::getCreated));
            if ("reverse".equals(order)) {
                transactionsByParams.sort(Comparator.comparing(Transaction::getCreated).reversed());
            }
        }

        return transactionsByParams;
    }

    private List<Transaction> getByOffset(List<Transaction> transactionsByParams, Integer offset, Integer limit) {
        Integer border = offset + limit;
        if (border > transactionsByParams.size()) {
            border = transactionsByParams.size();
        }
        transactionsByParams = transactionsByParams.subList(offset, border);

        return transactionsByParams;
    }

    private void generateTransactions() {
//        Transaction transaction1 = new Transaction();
//        transaction1.setId(1L);
//        transaction1.setMerchantId(0L);
//        transaction1.setAmount(new BigDecimal(10));
//        transaction1.setCurrency(Currency.EUR);
//        transaction1.setStatus(Transaction.Status.DEPOSITED);
//        transaction1.setCreated(LocalDateTime.now());
//        create(transaction1);
//
//        Transaction transaction2 = new Transaction();
//        transaction2.setId(2L);
//        transaction2.setMerchantId(2L);
//        transaction2.setAmount(new BigDecimal(12));
//        transaction2.setCurrency(Currency.USD);
//        transaction2.setStatus(Transaction.Status.DEPOSITED);
//        transaction2.setCreated(LocalDateTime.now());
//        create(transaction2);
//
//        Transaction transaction3 = new Transaction();
//        transaction3.setId(3L);
//        transaction3.setMerchantId(3L);
//        transaction3.setAmount(new BigDecimal(35));
//        transaction3.setCurrency(Currency.RUB);
//        transaction3.setStatus(Transaction.Status.DEPOSITED);
//        transaction3.setCreated(LocalDateTime.now());
//        create(transaction3);
//
//        Transaction transaction4 = new Transaction();
//        transaction4.setId(4L);
//        transaction4.setMerchantId(2L);
//        transaction4.setAmount(new BigDecimal(99));
//        transaction4.setCurrency(Currency.EUR);
//        transaction4.setStatus(Transaction.Status.DEPOSITED);
//        transaction4.setCreated(LocalDateTime.now());
//        create(transaction4);
//
//        Transaction transaction5 = new Transaction();
//        transaction5.setId(5L);
//        transaction5.setMerchantId(0L);
//        transaction5.setAmount(new BigDecimal(112));
//        transaction5.setCurrency(Currency.USD);
//        transaction5.setStatus(Transaction.Status.DEPOSITED);
//        transaction5.setCreated(LocalDateTime.now());
//        create(transaction5);
    }
}
