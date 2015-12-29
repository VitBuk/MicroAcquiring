package lv.lpb.rest;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import lv.lpb.database.DAO;
import lv.lpb.database.MerchantDAO;
import lv.lpb.domain.Merchant;
import lv.lpb.rest.errorHandling.AppException;
import lv.lpb.rest.errorHandling.Errors;

@Path("/merchants")
public class MerchantsResource {

    private MerchantDAO merchantDAO;

    @Inject
    public MerchantsResource(@DAO MerchantDAO merchantDAO) {
        this.merchantDAO = merchantDAO;
    }

    @GET
    @Path("/{merchantId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMerchant(@PathParam("merchantId") Long id) {
        Merchant merchant = merchantDAO.get(id);
        if (merchant == null) {
            throw new AppException(Response.Status.NOT_FOUND.getStatusCode(), Errors.MERCH_NOT_EXIST);
        }

        return Response.ok(merchant).build();
    }
}
