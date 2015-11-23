package lv.lpb.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import lv.lpb.domain.Merchant;
import lv.lpb.database.Merchants;

@Path("/merchants")
public class MerchantsResource {
    
    @GET
    @Path("/{merchantId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMerchant(@PathParam("merchantId") Long id) {
        Merchant merchant = Merchants.getById(id);
        
        if (merchant == null) {
            return Response.status(404).entity("Merchant is not exist").build();
        }
        
        return Response.ok(merchant).build();
    }
}