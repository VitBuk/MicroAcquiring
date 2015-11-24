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
        
    public @QueryParam(ID) String transactionId;
    public @QueryParam(MERCHANT_ID) String merchantId;
    public @QueryParam(CURRENCY) Currency currency;
    public @QueryParam(STATUS) Transaction.Status status;
    public @QueryParam(INIT_DATE) LocalDate initDate;
}
