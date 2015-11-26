package lv.lpb.database;

import java.util.List;
import java.util.Map;
import lv.lpb.domain.Merchant;

public interface MerchantCollectionDAO extends DAO<Merchant>{
    
    @Override
    public Merchant create(Merchant merchant);
    
    @Override
    public Merchant update(Merchant merchant); 
    
    @Override
    public Merchant get(Long id);
    
    @Override
    public List<Merchant> getAll();
    
    public List<Merchant> getByParams(Map<String, Object> filterParams, Map<String, Object> pageParams);
}
