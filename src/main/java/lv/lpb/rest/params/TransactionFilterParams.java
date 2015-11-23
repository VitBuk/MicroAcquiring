package lv.lpb.rest.params;

import java.time.LocalDate;
import javax.ws.rs.QueryParam;
import lv.lpb.domain.Currency;
import lv.lpb.domain.Transaction;

public class TransactionFilterParams {
    public static final String ID = "id";
    public static final String MERCHANT_ID = "merchantId";
    public static final String CURRENCY = "currency"; 
    public static final String STATUS = "status";
    public static final String INIT_DATE = "initDate";
        
    public @QueryParam("id") String transactionId;
    public @QueryParam("merchantId") String merchantId;
    public @QueryParam("currency") Currency currency;
    public @QueryParam("status") Transaction.Status status;
    public @QueryParam("initDate") LocalDate initDate;
}
