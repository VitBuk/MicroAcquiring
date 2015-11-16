package lv.lpb.database;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import lv.lpb.domain.Currency;
import lv.lpb.domain.Transaction;

public class Transactions {

    private static final List<Transaction> transactions = new ArrayList<>();

    static {
        generateHistory();
    }

    public static void add(Transaction transaction) {
        transactions.add(transaction);
    }

    public static List<Transaction> getTransactions() {
        return transactions;
    }

    public static Transaction getById(Long id) {
        for (Transaction transaction : transactions) {
            if (id.equals(transaction.getId())) {
                return transaction;
            }
        }

        return null;
    }

    public static void update(Transaction transaction) {
        transactions.remove(getById(transaction.getId()));
        transactions.add(transaction);
    }

    public static List<Transaction> getByMerchantId(Long merchantId) {
        List<Transaction> list = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (transaction.getMerchantId().equals(merchantId)) {
                list.add(transaction);
            }
        }

        return list;
    }

    private static void generateHistory() {
        Transaction transaction1 = new Transaction();
        transaction1.setId(1L);
        transaction1.setMerchantId(1L);
        transaction1.setAmount(new BigDecimal(10));
        transaction1.setCurrency(Currency.EUR);
        transaction1.setStatus(Transaction.Status.INIT);
        transaction1.setInitDate(LocalDate.of(2015, Month.NOVEMBER, 1));
        transactions.add(transaction1);

        Transaction transaction2 = new Transaction();
        transaction2.setId(2L);
        transaction2.setMerchantId(2L);
        transaction2.setAmount(new BigDecimal(12));
        transaction2.setCurrency(Currency.USD);
        transaction2.setStatus(Transaction.Status.INIT);
        transaction2.setInitDate(LocalDate.of(LocalDate.now().getYear(),
                LocalDate.now().getMonth(), LocalDate.now().getDayOfMonth() - 2));
        transactions.add(transaction2);

        Transaction transaction3 = new Transaction();
        transaction3.setId(3L);
        transaction3.setMerchantId(3L);
        transaction3.setAmount(new BigDecimal(35));
        transaction3.setCurrency(Currency.RUB);
        transaction3.setStatus(Transaction.Status.INIT);
        transaction3.setInitDate(LocalDate.of(2015, Month.NOVEMBER, 17));
        transactions.add(transaction3);

        Transaction transaction4 = new Transaction();
        transaction4.setId(4L);
        transaction4.setMerchantId(2L);
        transaction4.setAmount(new BigDecimal(99));
        transaction4.setCurrency(Currency.EUR);
        transaction4.setStatus(Transaction.Status.CLOSE);
        transaction4.setInitDate(LocalDate.of(2015, Month.NOVEMBER, 10));
        transactions.add(transaction4);

        Transaction transaction5 = new Transaction();
        transaction5.setId(5L);
        transaction5.setMerchantId(1L);
        transaction5.setAmount(new BigDecimal(112));
        transaction5.setCurrency(Currency.USD);
        transaction5.setStatus(Transaction.Status.INIT);
        transaction5.setInitDate(LocalDate.of(1970, Month.AUGUST, 20));
        transactions.add(transaction5);
    }
}
