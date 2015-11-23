package lv.lpb.database;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import lv.lpb.domain.Merchant;
import lv.lpb.rest.params.PageParams;

public class MerchantsManager {

    private static List<Merchant> merchants;

    public static List<Merchant> getMerchants(Map<String, Object> filterParams, Map<String,String> pageParams) {
        merchants = new ArrayList<>();
        
        System.out.println("filterParams: " + filterParams);
        filter (filterParams);
        sort(pageParams.get(PageParams.SORT), pageParams.get(PageParams.ORDER));
        merchantsByOffset(Integer.parseInt(pageParams.get(PageParams.OFFSET)), 
                Integer.parseInt(pageParams.get(PageParams.LIMIT)));
        
        return merchants;
    }

    private static void merchantsByOffset(Integer offset, Integer limit) {
        if (limit > merchants.size()) {
            limit = merchants.size();
        }
        merchants = merchants.subList(offset, limit);
    }

    private static void sort(String sortParam, String order) {
        if ("id".equals(sortParam)) {
            merchants.sort(Comparator.comparing(Merchant::getId));
            if ("reverse".equals(order)) {
                merchants.sort(Comparator.comparing(Merchant::getId).reversed());
            }
        }

        if ("initDate".equals(sortParam)) {
            merchants.sort(Comparator.comparing(Merchant::getStatus));
            if ("reverse".equals(order)) {
                merchants.sort(Comparator.comparing(Merchant::getStatus).reversed());
            }
        }

    }

    protected static void filter(Map<String, Object> filterParams) {
        // wtf
        System.out.println("filterParams: " + filterParams);
        for (Merchant merchant : Merchants.getMerchants()) {
            if (filterParams.get("id") == null || filterParams.get("id").equals(merchant.getId())) {
                if (filterParams.get("status") == null || filterParams.get("status").equals(merchant.getStatus())) {
                    merchants.add(merchant);
                }
            }
        }
    }
}
