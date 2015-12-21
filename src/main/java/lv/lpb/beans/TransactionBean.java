package lv.lpb.beans;

import java.util.List;
import lv.lpb.domain.Transaction;

public interface TransactionBean extends Bean<Transaction> {

    // only specific for TransactionBean
    
    public List<Transaction> lastDayTransactions();
}
