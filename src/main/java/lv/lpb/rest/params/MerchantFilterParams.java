package lv.lpb.rest.params;

import javax.ws.rs.QueryParam;
import lv.lpb.domain.Merchant;
import lv.lpb.domain.Transaction;

public class MerchantFilterParams {
    public static final String ID = "id";
    public static final String STATUS = "status";
        
    public @QueryParam("id") Long merchantId;
    public @QueryParam("status") Merchant.Status status;
}
