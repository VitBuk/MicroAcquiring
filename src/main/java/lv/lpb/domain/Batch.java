package lv.lpb.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Batch {
    private Long id;
    private List<Transaction> transactions = new ArrayList<>();
    private Long merchantId;
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
