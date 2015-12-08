package lv.lpb.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;
import java.time.LocalDate;

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
    private LocalDate created;
    // @XmlTransient
    @JsonIgnore
    private LocalDate updated; // last status update time
    
    private Long batchId;

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

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public LocalDate getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDate updated) {
        this.updated = updated;
    }

    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    @Override
    public String toString() {
        return "Transaction{" + "id=" + id + ", merchantId=" + merchantId + ", amount=" + amount + ", currency=" + currency + ", status=" + status + ", created=" + created + ", updated=" + updated + ", batchId=" + batchId + '}';
    }

    public static enum Status {
        //after transaction creation
        DEPOSITED,
        //after batch closing
        PROCESSED,
        //after transaction canceled (full/part)
        REVERSED,
        //after unsuccefull creation
        DECLINED;
    }
}
