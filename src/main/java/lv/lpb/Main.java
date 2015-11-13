package lv.lpb;

import java.math.BigDecimal;
import java.util.List;
import lv.lpb.database.Merchants;
import lv.lpb.database.Transactions;
import lv.lpb.domain.Currency;
import lv.lpb.domain.Merchant;
import lv.lpb.domain.Transaction;
import lv.lpb.domain.TransactionStatus;


public class Main {
    public static void main (String[] args) {
        Transaction transaction = new Transaction();
        transaction.setId(1L);
        transaction.setMerchantId(1L);
        transaction.setAmount(new BigDecimal(300));
        transaction.setCurrency(Currency.EUR);
        transaction.setTransactionStatus(TransactionStatus.INIT);
        
        Transactions.update(transaction);
        System.out.println(Transactions.getById(transaction.getId()));
        System.out.println("Transactions: " + Transactions.getTransactions());
       
        
    }
}
