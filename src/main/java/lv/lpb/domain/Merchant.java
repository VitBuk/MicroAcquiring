package lv.lpb.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.ws.rs.QueryParam;

public class Merchant {
    private Long id;
    private List<Currency> currencyList;
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
    
     public static class FilterParams {
        public static final String ID = "id";
        public static final String STATUS = "status";
        
        public @QueryParam("id") Long merchantId;
        public @QueryParam("status") Transaction.Status status;
    }
    
     public static class PageParams {
         public static final String OFFSET = "offset";
         public static final String LIMIT = "limit";
         public static final String SORT = "sort";
         public static final String ORDER = "order";
         
         public @QueryParam("offset") Integer offset; 
         public @QueryParam("limit") Integer limit;            
         public @QueryParam("sort") String sortParams;
         public @QueryParam("order") String order;
    }
}
