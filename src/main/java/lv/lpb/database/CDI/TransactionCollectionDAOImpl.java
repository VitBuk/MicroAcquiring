package lv.lpb.database.CDI;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import lv.lpb.database.TransactionCollectionDAO;
import lv.lpb.domain.Currency;
import lv.lpb.domain.Transaction;
import lv.lpb.rest.params.PageParams;

@ApplicationScoped
@Named("Transaction_CDI")
public class TransactionCollectionDAOImpl implements TransactionCollectionDAO {

    private List<Transaction> transactions = new CopyOnWriteArrayList();

    @PostConstruct
    public void init() {
        generateTransactions();
    }

    @Override
    public Transaction create(Transaction transaction) {
        transactions.add(transaction);
        return transaction;
    }

    @Override
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
    
    private List<Transaction> filter(List<Transaction> transactionsByParams, Map<String,Object> filterParams) {
        for (Transaction transaction : getAll()) {
            if (filterParams.get("merchantId") == null || filterParams.get("merchantId").equals(transaction.getMerchantId())) {
                if (filterParams.get("currency") == null || filterParams.get("currency").equals(transaction.getCurrency())) {
                    if (filterParams.get("status") == null || filterParams.get("status").equals(transaction.getStatus())) {
                        if (filterParams.get("initDate") == null || filterParams.get("initDate").equals(transaction.getCreated())) {
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
            if("reverse".equals(order)) {
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
        Transaction transaction1 = new Transaction();
        transaction1.setId(1L);
        transaction1.setMerchantId(1L);
        transaction1.setAmount(new BigDecimal(10));
        transaction1.setCurrency(Currency.EUR);
        transaction1.setStatus(Transaction.Status.INIT);
        transaction1.setCreated(LocalDate.of(2015, Month.NOVEMBER, 1));
        create(transaction1);

        Transaction transaction2 = new Transaction();
        transaction2.setId(2L);
        transaction2.setMerchantId(2L);
        transaction2.setAmount(new BigDecimal(12));
        transaction2.setCurrency(Currency.USD);
        transaction2.setStatus(Transaction.Status.INIT);
        transaction2.setCreated(LocalDate.of(LocalDate.now().getYear(),
                LocalDate.now().getMonth(), LocalDate.now().getDayOfMonth() - 2));
        create(transaction2);

        Transaction transaction3 = new Transaction();
        transaction3.setId(3L);
        transaction3.setMerchantId(3L);
        transaction3.setAmount(new BigDecimal(35));
        transaction3.setCurrency(Currency.RUB);
        transaction3.setStatus(Transaction.Status.INIT);
        transaction3.setCreated(LocalDate.of(2015, Month.NOVEMBER, 17));
        create(transaction3);

        Transaction transaction4 = new Transaction();
        transaction4.setId(4L);
        transaction4.setMerchantId(2L);
        transaction4.setAmount(new BigDecimal(99));
        transaction4.setCurrency(Currency.EUR);
        transaction4.setStatus(Transaction.Status.CLOSE);
        transaction4.setCreated(LocalDate.of(2015, Month.NOVEMBER, 10));
        create(transaction4);

        Transaction transaction5 = new Transaction();
        transaction5.setId(5L);
        transaction5.setMerchantId(1L);
        transaction5.setAmount(new BigDecimal(112));
        transaction5.setCurrency(Currency.USD);
        transaction5.setStatus(Transaction.Status.INIT);
        transaction5.setCreated(LocalDate.of(1970, Month.AUGUST, 20));
        create(transaction5);
    }
}
