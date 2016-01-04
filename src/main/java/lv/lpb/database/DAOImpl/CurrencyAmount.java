package lv.lpb.database.DAOImpl;

import java.math.BigDecimal;
import lv.lpb.domain.Currency;

public class CurrencyAmount {
    private Currency currency;
    private BigDecimal amount;

    public CurrencyAmount(Currency currency, BigDecimal amount) {
        this.currency = currency;
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    
}
