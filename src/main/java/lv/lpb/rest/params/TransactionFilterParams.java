package lv.lpb.rest.params;

import java.time.LocalDateTime;
import javax.ws.rs.QueryParam;
import lv.lpb.domain.Currency;
import lv.lpb.domain.Merchant;
import lv.lpb.domain.Transaction;

public class TransactionFilterParams {
    public static final String ID = "id";
    public static final String MERCHANT = "merchant";
    public static final String CURRENCY = "currency"; 
    public static final String STATUS = "status";
    public static final String CREATED = "created";
        
    public @QueryParam(ID) String transactionId;
    public @QueryParam(MERCHANT) Merchant merchant;
    public @QueryParam(CURRENCY) Currency currency;
    public @QueryParam(STATUS) Transaction.Status status;
    public @QueryParam(CREATED) LocalDateTime created;
}
