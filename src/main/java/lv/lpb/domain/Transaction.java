package lv.lpb.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


//@XmlRootElement(name="Transaction")
public class Transaction {

    //@XmlElement(name="id")
    private Long id;
    //@XmlElement(name="merchantId")
    private Long merchantId;
    //@XmlElement(name="amount")
    private BigDecimal amount;
    //@XmlElement(name="currency")
    private Currency currency;
    //@XmlElement(name="status")
    private Status status;
   // @XmlElement(name="initDate")
    private LocalDate initDate;
//    @XmlTransient
    @JsonIgnore
    private LocalDate updated; // last status update time

    public Transaction() {}
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getInitDate() {
        return initDate;
    }

    public void setInitDate(LocalDate initDate) {
        this.initDate = initDate;
    }

    public LocalDate getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDate updated) {
        this.updated = updated;
    }

    @Override
    public String toString() {
        return "Transaction{" + "id=" + id + ", merchantId=" + merchantId + ", amount=" + amount + ", currency=" + currency + ", status=" + status + ", initDate=" + initDate + '}';
    }
    
    public static enum Status {
        INIT("Initialized"),
        CANCEL("Canceled"),
        CANCEL_PART("Canceled in a part"),
        CLOSE("Closed");

        private String name;

        Status(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
