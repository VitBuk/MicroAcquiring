package lv.lpb.beans;

import java.util.List;
import lv.lpb.domain.Transaction;

public interface TransactionBean extends Bean<Transaction> {

    @Override
    public void persist(Transaction transaction);

    @Override
    public Transaction find(Long id);

    @Override
    public void update(Transaction transaction);

    @Override
    public void delete(Long id);
    
    @Override
    public List<Transaction> getAll();
}
