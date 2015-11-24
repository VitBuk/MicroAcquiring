package lv.lpb.database;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import lv.lpb.domain.Currency;
import lv.lpb.domain.Merchant;
import lv.lpb.rest.params.PageParams;

public class MerchantCollectionDAO implements DAO<Merchant> {

    private List<Merchant> merchants = new CopyOnWriteArrayList();

    private static final MerchantCollectionDAO merchantDAO = new MerchantCollectionDAO();

    static {
        //init test merchants
        Merchant merchant1 = new Merchant(1L);
        merchant1.add(Currency.JPY);
        merchant1.setStatus(Merchant.Status.ACTIVE);
        merchantDAO.create(merchant1);
        Merchant merchant2 = new Merchant(2L);
        merchant2.add(Currency.RUB);
        merchant2.setStatus(Merchant.Status.ACTIVE);
        merchantDAO.create(merchant2);
        Merchant merchant3 = new Merchant(3L);
        merchant3.add(Currency.GBP);
        merchant3.add(Currency.RUB);
        merchant3.setStatus(Merchant.Status.ACTIVE);
        merchantDAO.create(merchant3);
    }

    public MerchantCollectionDAO getInstance() {
        return merchantDAO;
    }

    @Override
    public Merchant create(Merchant merchant) {
        merchants.add(merchant);
        return merchant;
    }

    @Override
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

    public List<Merchant> getByParams(Map<String, Object> filterParams, Map<String, Object> pageParams) {
        List<Merchant> merchantsByParams = new ArrayList<>();
        merchantsByParams = filter(merchantsByParams, filterParams);
        merchantsByParams = sort(merchantsByParams, pageParams.get(PageParams.SORT) + "",
                pageParams.get(PageParams.ORDER) + "");

        if ((pageParams.get(PageParams.OFFSET) instanceof java.lang.Object) == true
                && (pageParams.get(PageParams.LIMIT) instanceof java.lang.Object) == true) {
            merchantsByParams = getByOffset(merchantsByParams, (Integer) pageParams.get(PageParams.OFFSET),
                    (Integer) pageParams.get(PageParams.LIMIT));
        }

        System.out.println(merchantsByParams);
        return merchantsByParams;
    }

    private List<Merchant> filter(List<Merchant> merchantsByParams, Map<String, Object> filterParams) {
        System.out.println(merchantsByParams);
        for (Merchant merchant : getAll()) {
            if (filterParams.get("id") == null || filterParams.get("id").equals(merchant.getId())) {
                if (filterParams.get("status") == null || filterParams.get("status").equals(merchant.getStatus())) {
                    merchantsByParams.add(merchant);
                }
            }
        }
        
        return merchantsByParams;
    }

    private List<Merchant> sort(List<Merchant> merchantsByParams, String sortParam, String order) {
        if ("id".equals(sortParam)) {
            merchantsByParams.sort(Comparator.comparing(Merchant::getId));
            if ("reverse".equals(order)) {
                merchantsByParams.sort(Comparator.comparing(Merchant::getId).reversed());
            }
        }

        if ("initDate".equals(sortParam)) {
            merchantsByParams.sort(Comparator.comparing(Merchant::getStatus));
            if ("reverse".equals(order)) {
                merchantsByParams.sort(Comparator.comparing(Merchant::getStatus).reversed());
            }
        }

        return merchantsByParams;
    }

    private List<Merchant> getByOffset(List<Merchant> merchantsByParams, Integer offset, Integer limit) {
        if (limit > merchantsByParams.size()) {
            limit = merchantsByParams.size();
        }
        merchantsByParams = merchantsByParams.subList(offset, limit);
        
        return merchantsByParams;
    }
}
