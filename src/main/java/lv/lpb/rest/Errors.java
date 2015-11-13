package lv.lpb.rest;

public class Errors {
    public static final String CANCEL_CLOSED = "You cant cancel closed transaction";
    public static final String CANCEL_CANCELED = "You cant cancel canceled transaction";
    public static final String CANCEL_LIMIT_EXCESS = "Cancelable amount bigger than transaction amount";
    public static final String CANCEL_ZERO = "You wanna cancel transaction for 0 amount, lol";
    public static final String CANCEL_WRONG_CURRENCY = "Wrong currency";
    
    public static final String MERCH_NOT_EXIST = "Merchant is not exist";
    public static final String MERCH_HAVENT_TRAN = "This merchant has not transactions";
    
    public static final String MERCH_INACTIVE = "This merchant is inactive";
    public static final String UNALLOWED_CURRENCY = "That type of currency not allowed";
    
    public static final String MERCHS_ZERO = "We have not any merchants in our system";
    
    public static final String TRANS_ZERO = "We have not any transactions in our system";
}
