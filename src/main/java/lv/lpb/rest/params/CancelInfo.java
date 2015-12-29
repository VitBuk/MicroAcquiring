package lv.lpb.rest.params;

import java.math.BigDecimal;
import lv.lpb.domain.Currency;

public class CancelInfo {
    
    private Long transactionId;
    private BigDecimal amount;
    private Currency currency;

    public CancelInfo() {
    }
    
    public Long getTransactionId() {
        return transactionId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }
}
