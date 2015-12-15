package lv.lpb.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "BATCH")
@NamedQueries({
    @NamedQuery(name = "Batch.findAll", query = "SELECT b FROM Batch b")
})
public class Batch implements Serializable{
    private static final long serialVersionUID = 0L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column
    private List<Transaction> transactions = new ArrayList<>();
    
    @Column (name = "MERCHANT_ID")
    private Long merchantId;
    
    @Column
    private LocalDate date;

    public Batch(Long merchantId, LocalDate date) {
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
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
