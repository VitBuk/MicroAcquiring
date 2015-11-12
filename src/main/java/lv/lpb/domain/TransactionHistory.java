package lv.lpb.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TransactionHistory {
    private final List<Transaction> transactions;
    
    public TransactionHistory() {
        transactions = new ArrayList<>();
        generateHistory();
    }
    
    public void add(Transaction transaction) {
        transactions.add(transaction);
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
    
    public List<Transaction> getByMerchant(Merchant merchant) {
        List<Transaction> list = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (transaction.getMerchant().equals(merchant)) {
                list.add(transaction);
            }
        }
        
        return list;
    }
    
    private void generateHistory() {
        Merchant merchant1 = new Merchant(1L);
        Merchant merchant2 = new Merchant(2L);
        merchant2.add(Currency.RUB);
        Merchant merchant3 = new Merchant(3L);
        
        Transaction transaction1 = new Transaction();
        transaction1.setId(1L);
        transaction1.setMerchant(merchant1);
        transaction1.setAmount(new BigDecimal(10));
        transaction1.setCurrency(Currency.EUR);
        add(transaction1);
        
        Transaction transaction2 = new Transaction();
        transaction2.setId(2L);
        transaction2.setMerchant(merchant2);
        transaction2.setAmount(new BigDecimal(12));
        transaction2.setCurrency(Currency.USD);
        add(transaction2);
        
        Transaction transaction3 = new Transaction();
        transaction3.setId(3L);
        transaction3.setMerchant(merchant3);
        transaction3.setAmount(new BigDecimal(35.2));
        transaction3.setCurrency(Currency.RUB);
        add(transaction3);

        Transaction transaction4 = new Transaction();
        transaction4.setId(4L);
        transaction4.setMerchant(merchant2);
        transaction4.setAmount(new BigDecimal(99.9));
        transaction4.setCurrency(Currency.EUR);
        add(transaction4);
        
        Transaction transaction5 = new Transaction();
        transaction5.setId(5L);
        transaction5.setMerchant(merchant1);
        transaction5.setAmount(new BigDecimal(112));
        transaction5.setCurrency(Currency.USD);
        add(transaction5);
    }
}
