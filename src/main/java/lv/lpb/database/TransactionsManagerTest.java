//package lv.lpb.database;
//
//import java.util.Comparator;
//import java.util.List;
//import java.util.Map;
//import lv.lpb.domain.Transaction;
//
//public class TransactionsManager {
//    private static List<Transaction> transactions;
//    
//    
//    public static List<Transaction> getTransactions(Map<String,Object> filterParams, 
//            String sortParams, String order, Integer offset, Integer limit) {
//        if (filterParams == null) {
//            transactions = Transactions.getTransactions();
//        } else {
//             filter(filterParams);
//        }
//        if (sortParams != null) {
//            sort(sortParams, order);
//        } 
//        transactionsByOffset(offset, limit);
//        return transactions;
//    }
//    
//    private static void filter(Map<String, Object> filterParams) {
//        //wtf
//        for (Transaction transaction : Transactions.getTransactions()) {
//            if (filterParams.get("merchantId") == null || filterParams.get("merchantId").equals(transaction.getMerchantId())) {
//                if (filterParams.get("currency") == null || filterParams.get("currency").equals(transaction.getCurrency())) {
//                    if (filterParams.get("status") == null || filterParams.get("status").equals(transaction.getStatus())) {
//                        if (filterParams.get("initDate") == null || filterParams.get("initDate").equals(transaction.getInitDate())) {
//                            transactions.add(transaction);
//                        }
//                    }
//                }
//            }
//        }
//    }
//    
//    private static void sort(String sortParams, String order) {
//        Comparator comparator = null;
//        if ("id".equals(sortParams)) {
//            comparator = Comparator.comparing(Transaction::getId);
//        } else if ("initDate".equals(sortParams)) {
//            comparator = Comparator.comparing(Transaction::getInitDate);
//        }
//        
//        if ("reverse".equals(order)) {
//            comparator.reversed();
//        }
//        transactions.sort(comparator);
//        
//    }
//
//    private static void transactionsByOffset(Integer offset, Integer limit) {
//        if (limit > transactions.size()) {
//            limit = transactions.size();
//        }
//        transactions.subList(offset, limit);
//    } 
//}
