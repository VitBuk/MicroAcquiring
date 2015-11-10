package lv.lpb.domain;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main (String[] args) {
        List<Merchant> merchantList = new ArrayList<Merchant>();

        Merchant merchant = new Merchant();
        Merchant merchant2 = new Merchant();
        merchant2.add(Currency.RUB);

        merchantList.add(merchant);
        merchantList.add(merchant2);
        
        System.out.println(merchantList);
        
    }
}
