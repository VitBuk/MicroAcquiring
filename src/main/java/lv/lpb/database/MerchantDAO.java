package lv.lpb.database;

import java.util.List;
import java.util.Map;
import lv.lpb.domain.Merchant;

public interface MerchantDAO extends DAO<Merchant> {
    
    public List<Merchant> getByParams(Map<String, Object> filterParams, Map<String, Object> pageParams);
    
}
