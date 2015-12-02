package lv.lpb.database;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import lv.lpb.domain.Transaction;

public interface TransactionCollectionDAO extends DAO<Transaction> {
    
    @Override
    public Transaction create(Transaction transaction);
    
    @Override
    public Transaction update(Transaction transaction); 
    
    @Override
    public Transaction get(Long id);
    
    @Override
    public List<Transaction> getAll();
    
    public List<Transaction> getByParams(Map<String, Object> filterParams, Map<String, Object> pageParams);
    
    public BigDecimal dayTotalAmount();
}
