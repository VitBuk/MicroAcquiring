package lv.lpb.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;
import lv.lpb.Constants;

@Entity
@Table(name = "TRANSACTION")
@NamedQueries({
    @NamedQuery(name = "Transaction.findAll", query = "SELECT t FROM Transaction t")
})
public class Transaction implements Serializable {

    private static final long serialVersionUID = 0L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "MERCHANT")
    private Merchant merchant;

    @Column
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(length = Constants.CURRENCY_LENGTH)
    private Currency currency;

    @Enumerated(EnumType.STRING)
    @Column(length = Constants.TRAN_STATUS_MAX_LENGTH)
    private Status status;

    @Column
    private LocalDateTime created;

    @JsonIgnore
    @Transient
    private LocalDateTime updated; // last status update time

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

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Transaction{" + "id=" + id + ", merchant=" + merchant + ", amount=" + amount + ", currency=" + currency + ", status=" + status + ", created=" + created + ", updated=" + updated + ", version=" + version + '}';
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
