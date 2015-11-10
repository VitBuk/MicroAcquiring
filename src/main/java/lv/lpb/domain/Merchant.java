package lv.lpb.domain;

import java.util.ArrayList;
import java.util.List;

public class Merchant {
    
    private final List<Currency> currencyList;
    
    public Merchant() {
        this.currencyList = new ArrayList<>();
        currencyList.add(Currency.EUR);
        currencyList.add(Currency.USD);
    }
    
    public void add(Currency currency) {
        currencyList.add(currency);
    }
    
    public List<Currency> getCurrencyList() {
        return currencyList;
    }

    @Override
    public String toString() {
        return "Merchant{" + "currencyList=" + currencyList + '}';
    }
}
