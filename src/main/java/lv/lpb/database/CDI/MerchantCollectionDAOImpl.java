package lv.lpb.database.CDI;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import lv.lpb.database.MerchantCollectionDAO;
import lv.lpb.domain.Currency;
import lv.lpb.domain.Merchant;
import lv.lpb.rest.params.PageParams;

@ApplicationScoped
@Named("Merchant_CDI")
public class MerchantCollectionDAOImpl implements MerchantCollectionDAO{

    private List<Merchant> merchants = new CopyOnWriteArrayList();

    @PostConstruct
    public void init() {
        generateMerchants();    
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

    @Override
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

        return merchantsByParams;
    }

    private List<Merchant> filter(List<Merchant> merchantsByParams, Map<String, Object> filterParams) {
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
        Integer border = offset + limit;
        if (border > merchantsByParams.size()) {
            border = merchantsByParams.size();
        }
        merchantsByParams = merchantsByParams.subList(offset, border);
        
        return merchantsByParams;
    }
    
    private void generateMerchants() {
        Merchant merchant1 = new Merchant(1L);
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
