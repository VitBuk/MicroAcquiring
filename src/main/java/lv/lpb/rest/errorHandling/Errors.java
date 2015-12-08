package lv.lpb.rest.errorHandling;

public class Errors {
    public static final String CANCEL_DECLINED = "You cant cancel declined transaction";
    public static final String CANCEL_REVERSED = "You cant cancel reversed transaction";
    public static final String CANCEL_LIMIT_EXCESS = "Cancelable amount bigger than transaction amount";
    public static final String CANCEL_ZERO = "You wanna cancel transaction for 0 amount, lol";
    public static final String CANCEL_WRONG_CURRENCY = "Wrong currency";
    public static final String CANCEL_OVERDUE = "You cant cancel transaction, cause it was initialized more than three days ago";
    
    public static final String MERCH_NOT_EXIST = "Merchant is not exist";
    
    public static final String MERCH_INACTIVE = "This merchant is inactive";
    public static final String UNALLOWED_CURRENCY = "That type of currency not allowed";
    
    public static final String MERCHS_ZERO = "We have not any merchants in our system";
    
    public static final String TRANS_ZERO = "We have not any transactions in our system";
}
