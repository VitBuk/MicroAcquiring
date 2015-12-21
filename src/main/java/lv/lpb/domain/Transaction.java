package lv.lpb.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

//@XmlRootElement(name="Transaction")
@Entity
@Table(name = "TRANSACTION")
@NamedQueries({
    @NamedQuery(name = "Transaction.findAll", query = "SELECT t FROM Transaction t")
})
public class Transaction implements Serializable {

    private static final long serialVersionUID = 0L;

    //@XmlElement(name="id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@XmlElement(name="merchantId")
    @Column(name = "MERCHANT_ID")
    private Long merchantId;

    //@XmlElement(name="amount")
    @Column
    private BigDecimal amount;

    //@XmlElement(name="currency")
    @Enumerated(EnumType.STRING)
    private Currency currency;

    //@XmlElement(name="status")
    @Enumerated(EnumType.STRING)
    private Status status;

    // @XmlElement(name="initDate")
    // TODO: check how map LocalDate with JPA
    @Column
    private LocalDateTime created;

    // @XmlTransient
    @JsonIgnore
    @Transient
    private LocalDateTime updated; // last status update time

    @Column(name = "BATCH_ID")
    private Long batchId;

    @Version
    private int version;
    
    public Transaction() {
    }

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

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    public boolean inBatch() {
        return getBatchId() != null;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Transaction{" + "id=" + id + ", merchantId=" + merchantId + ", amount=" + amount + ", currency=" + currency + ", status=" + status + ", created=" + created + ", updated=" + updated + ", batchId=" + batchId + ", version=" + version + '}';
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
