package lv.lpb.database;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import lv.lpb.domain.Currency;
import lv.lpb.domain.Merchant;
import lv.lpb.domain.Transaction;

public interface TransactionDAO extends GenericDAO<Transaction> {

    public List<Transaction> getByParams(Map<String, Object> filterParams, Map<String, Object> pageParams);

    public List<Transaction> lastDayTransactions();

    public List<Transaction> getByMerchant(Merchant merchant);
    
    public Map<Currency, BigDecimal> dayTotalAmount();
}
