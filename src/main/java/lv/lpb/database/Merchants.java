package lv.lpb.database;

import java.util.ArrayList;
import java.util.List;
import lv.lpb.domain.Currency;
import lv.lpb.domain.Merchant;

public class Merchants {

    private static final List<Merchant> merchants;
    
    static {
        merchants = new ArrayList<>();
        generateMerchants();
    }

    public static void add(Merchant merchant) {
        merchants.add(merchant);
    }

    public static List<Merchant> getMerchants() {
        return merchants;
    }

    public static Merchant getById(Long id) {
        for (Merchant merchant : merchants) {
            if (id.equals(merchant.getId())) {
                return merchant;
            }
        }

        return null;
    }

    private static void generateMerchants() {
        Merchant merchant1 = new Merchant(1L);
        merchants.add(merchant1);
        merchant1.add(Currency.JPY);
        Merchant merchant2 = new Merchant(2L);
        merchant2.add(Currency.RUB);
        merchants.add(merchant2);
        Merchant merchant3 = new Merchant(3L);
        merchants.add(merchant3);
        merchant3.add(Currency.GBP);
        merchant3.add(Currency.RUB);
    }
}
