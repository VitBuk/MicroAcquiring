package lv.lpb.rest.params;

import javax.ws.rs.QueryParam;
import lv.lpb.domain.Merchant;

public class MerchantFilterParams {
    public static final String ID = "id";
    public static final String STATUS = "status";
        
    public @QueryParam(ID) Long merchantId;
    public @QueryParam(STATUS) Merchant.Status status;
}
