package lv.lpb.domain;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
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
