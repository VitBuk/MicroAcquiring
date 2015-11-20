package lv.lpb.database;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import lv.lpb.domain.Transaction;

public class TransactionsManager {
    private static List<Transaction> transactions;
    
    public static List<Transaction> getTransactions(Map<String, Object> filterParams, String sortParam, String order, Integer offset, Integer limit){
        transactions = new ArrayList<Transaction>();
        filter(filterParams);
        sort(sortParam, order);
        transactionsByOffset(offset, limit);
        
        return transactions;
    }
    
    private static void transactionsByOffset(Integer offset, Integer limit) {
        if (limit > transactions.size()) {
            limit = transactions.size();
        }
        transactions = transactions.subList(offset, limit);
    }
    
    private static void sort(String sortParam, String order) {
     if ("id".equals(sortParam)) {
            transactions.sort(Comparator.comparing(Transaction::getId));
            if ("reverse".equals(order)) {
                transactions.sort(Comparator.comparing(Transaction::getId).reversed());
            }
        }
        
        if ("initDate".equals(sortParam)) {
            transactions.sort(Comparator.comparing(Transaction::getInitDate));
            if("reverse".equals(order)) {
                transactions.sort(Comparator.comparing(Transaction::getInitDate).reversed());
            }
        }
        
    }
    
    protected static void filter(Map<String,Object> filterParams) {
        // wtf
        for (Transaction transaction : Transactions.getTransactions()) {
            if (filterParams.get("merchantId") == null || filterParams.get("merchantId").equals(transaction.getMerchantId())) {
                if (filterParams.get("currency") == null || filterParams.get("currency").equals(transaction.getCurrency())) {
                    if (filterParams.get("status") == null || filterParams.get("status").equals(transaction.getStatus())) {
                        if (filterParams.get("initDate") == null || filterParams.get("initDate").equals(transaction.getInitDate())) {
                            transactions.add(transaction);
                        }
                    }
                }
            }
        }
    }
}
