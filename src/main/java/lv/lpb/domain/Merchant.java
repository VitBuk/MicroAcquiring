package lv.lpb.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

@Entity
@Table(name = "MERCHANT")
@NamedQueries({
    @NamedQuery(name = "Merchant.findAll", query = "SELECT m FROM Merchant m")
})
public class Merchant implements Serializable {
    private static final long serialVersionUID = 0L;
    
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;
    
    @Column (name = "CURRENCY_LIST")
    private List<Currency> currencyList;
    
    @Enumerated(EnumType.STRING)
    private Status status;
    
    public Merchant() {
    }
    
    public Merchant(Long id) {
        this.id = id; 
        this.currencyList = new ArrayList<>();
        currencyList.add(Currency.EUR);
        currencyList.add(Currency.USD);
    }
    
    public void add(Currency currency) {
        currencyList.add(currency);
    }

    public Long getId() {
        return id;
    }
    
    public List<Currency> getCurrencyList() {
        return currencyList;
    }

    public boolean allowedCurrency(Currency currency) {
        return currencyList.contains(currency);
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Merchant{" + "id=" + id + ", currencyList=" + currencyList + ", status=" + status + '}';
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Merchant other = (Merchant) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    } 
    
    public static enum Status {
        ACTIVE, INACTIVE;
    }
}