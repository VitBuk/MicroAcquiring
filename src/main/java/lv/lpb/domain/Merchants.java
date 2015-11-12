package lv.lpb.domain;

import java.util.ArrayList;
import java.util.List;

public class Merchants {

    private final List<Merchant> merchants;

    public Merchants() {
        this.merchants = new ArrayList<>();
        generateMerchants();
    }

    public void add(Merchant merchant) {
        merchants.add(merchant);
    }

    public List<Merchant> getMerchants() {
        return merchants;
    }

    public Merchant getById(Long id) {
        for (Merchant merchant : merchants) {
            if (id.equals(merchant.getId())) {
                return merchant;
            }
        }

        return null;
    }

    private void generateMerchants() {
        Merchant merchant1 = new Merchant(1L);
        merchants.add(merchant1);
        Merchant merchant2 = new Merchant(2L);
        merchant2.add(Currency.RUB);
        merchants.add(merchant2);
        Merchant merchant3 = new Merchant(3L);
        merchants.add(merchant3);
    }
}
