package lv.lpb.businessLogic;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.enterprise.context.ApplicationScoped;
import lv.lpb.domain.CancelInfo;
import lv.lpb.domain.Transaction;

@ApplicationScoped
public class TransactionsService {
    
    public Transaction cancel(Transaction transaction, CancelInfo cancelInfo) {
        transaction.setAmount(transaction.getAmount().subtract(cancelInfo.getAmount()));

        if (transaction.getAmount().compareTo(BigDecimal.ZERO) == 0) {
            transaction.setStatus(Transaction.Status.CANCEL);
        } else {
            transaction.setStatus(Transaction.Status.CANCEL_PART);
        }

        transaction.setUpdated(LocalDate.now());
        
        return transaction;
    }
}
