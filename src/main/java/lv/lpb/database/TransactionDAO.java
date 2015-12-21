package lv.lpb.database;

import java.util.List;
import java.util.Map;
import lv.lpb.domain.Transaction;

public interface TransactionDAO extends DAO<Transaction> {

    public List<Transaction> getByParams(Map<String, Object> filterParams, Map<String, Object> pageParams);

    public List<Transaction> lastDayTransactions();

    public List<Transaction> getByMerchantId(Long merchantId);
}
