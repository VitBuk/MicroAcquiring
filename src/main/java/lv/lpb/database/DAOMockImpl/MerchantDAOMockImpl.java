package lv.lpb.database.DAOMockImpl;

import java.util.ArrayList;
import java.util.Comparator;
import lv.lpb.database.DAO;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.Lock;
import static javax.ejb.LockType.READ;
import static javax.ejb.LockType.WRITE;
import javax.ejb.Singleton;
import lv.lpb.Constants;
import lv.lpb.database.DAO.Type;
import lv.lpb.database.MerchantDAO;
import lv.lpb.domain.Currency;
import lv.lpb.domain.Merchant;
import lv.lpb.rest.params.PageParams;

@Singleton
@DAO(Type.COLLECTION)
@Lock(READ)
public class MerchantDAOMockImpl implements MerchantDAO {

    private List<Merchant> merchants = new ArrayList<>();

    @PostConstruct
    public void init() {
        generateMerchants();
    }

    @Override
    @Lock(WRITE)
    public Merchant create(Merchant merchant) {
        merchants.add(merchant);
        return merchant;
    }

    @Override
    @Lock(WRITE)
    public Merchant update(Merchant merchant) {
        merchants.remove(get(merchant.getId()));
        merchants.add(merchant);
        return merchant;
    }

    @Override
    public Merchant get(Long id) {
        for (Merchant merchant : merchants) {
            if (id.equals(merchant.getId())) {
                return merchant;
            }
        }

        return null;
    }

    @Override
    public List<Merchant> getAll() {
        return merchants;
    }

    @Override
    public List<Merchant> getByParams(Map<String, Object> filterParams, Map<String, Object> pageParams) {
        List<Merchant> merchantsByParams = new ArrayList<>();
        merchantsByParams = filter(merchantsByParams, filterParams);
        merchantsByParams = sort(merchantsByParams, pageParams);
//        
//        String.valueOf(pageParams.get(PageParams.SORT)),
//                String.valueOf(pageParams.get(PageParams.ORDER))

        if ((pageParams.get(PageParams.OFFSET) != null && (pageParams.get(PageParams.LIMIT) != null))) {
            merchantsByParams = getByOffset(merchantsByParams, (Integer) pageParams.get(PageParams.OFFSET),
                    (Integer) pageParams.get(PageParams.LIMIT));
        }

        return merchantsByParams;
    }

    private List<Merchant> filter(List<Merchant> merchantsByParams, Map<String, Object> filterParams) {
        for (Merchant merchant : getAll()) {
            hasMerchant(merchant, filterParams);
        }

        return merchantsByParams;
    }

    private boolean hasMerchant(Merchant merchant, Map<String, Object> filterParams) {
        if (filterParams.get("id") == null || filterParams.get("id").equals(merchant.getId())) {
            if (filterParams.get("status") == null || filterParams.get("status").equals(merchant.getStatus())) {
                return true;
            }
        }

        return false;
    }

    private List<Merchant> sort(List<Merchant> merchantsByParams, Map<String, Object> sortParams) {
        Comparator<Merchant> comparator = null;

        if ("id".equals(String.valueOf(sortParams.get(PageParams.SORT)))
                && "created".equals(String.valueOf(sortParams.get(PageParams.SORT)))) {
            comparator = Comparator.comparing(Merchant::getCreated).thenComparing(Merchant::getId);
        } else if ("id".equals(String.valueOf(sortParams.get(PageParams.SORT)))) {
            comparator = Comparator.comparing(Merchant::getId);

            if ("created".equals(String.valueOf(sortParams.get(PageParams.SORT)))) {
                comparator = Comparator.comparing(Merchant::getCreated);
            }

        }

        if ("reverse".equals(String.valueOf(sortParams.get(PageParams.ORDER)))) {
            merchantsByParams.sort(comparator.reversed());
        } else {
            merchantsByParams.sort(comparator);
        }
        return merchantsByParams;
    }

    private List<Merchant> getByOffset(List<Merchant> merchantsByParams, Integer offset, Integer limit) {
        Integer border = offset + limit;
        if (border > merchantsByParams.size()) {
            border = merchantsByParams.size();
        }
        merchantsByParams = merchantsByParams.subList(offset, border);

        return merchantsByParams;
    }

    private void generateMerchants() {
        Merchant merchant1 = new Merchant(Constants.TEST_MERCHANT_ID);
        merchant1.add(Currency.JPY);
        merchant1.setStatus(Merchant.Status.ACTIVE);
        create(merchant1);

        Merchant merchant2 = new Merchant(2L);
        merchant2.add(Currency.RUB);
        merchant2.setStatus(Merchant.Status.ACTIVE);
        create(merchant2);

        Merchant merchant3 = new Merchant(3L);
        merchant3.add(Currency.GBP);
        merchant3.add(Currency.RUB);
        merchant3.setStatus(Merchant.Status.ACTIVE);
        create(merchant3);
    }
}
