package lv.lpb.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Merchant {
    private Long id;
    private List<Currency> currencyList;
    private MerchantStatus status;
    
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

    public MerchantStatus getStatus() {
        return status;
    }

    public void setStatus(MerchantStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Merchant{" + "id=" + id + ", currencyList=" + currencyList + ", merchantStatus=" + status + '}';
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
}
