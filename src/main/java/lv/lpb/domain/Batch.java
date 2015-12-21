package lv.lpb.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "BATCH")
@NamedQueries({
    @NamedQuery(name = "Batch.findAll", query = "SELECT b FROM Batch b")
})
public class Batch implements Serializable {

    private static final long serialVersionUID = 0L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @OneToMany(fetch = FetchType.LAZY)
//    @OrderColumn(name = "TRANSACTIONS")
    @JoinColumn(name = "BATCH_ID")
    private List<Transaction> transactions = new ArrayList<>();

    @Column(name = "MERCHANT_ID")
    //Todo: private Merchant merchant
    private Long merchantId;

    @Column
    private LocalDateTime date;

    public Batch() {
    }

    public Batch(Long merchantId, LocalDateTime date) {
        this.merchantId = merchantId;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void add(Transaction transaction) {
        transactions.add(transaction);
    }

    @Override
    public String toString() {
        return "Batch{" + "id=" + id + ", transactions=" + transactions + ", merchantId=" + merchantId + ", date=" + date + '}';
    }

}
