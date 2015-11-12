package lv.lpb.domain;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Transaction {
    private Long id;
    private Merchant merchant;
    private BigDecimal amount;
    private Currency currency; 
//    private Status status;
//    private String discription;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "Transaction{" + "id=" + id + ", merchant=" + merchant + ", amount=" + amount + ", currency=" + currency + '}';
    }
}
