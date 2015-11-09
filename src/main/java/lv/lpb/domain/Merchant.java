package lv.lpb.domain;

import java.util.ArrayList;
import java.util.List;

public class Merchant {
    
    private List<Currency> currencyList = new ArrayList<>();
    
    public Merchant() {
        currencyList.add(Currency.EUR);
        currencyList.add(Currency.USD);
    }
    
    public void addCurrency(Currency currency) {
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
