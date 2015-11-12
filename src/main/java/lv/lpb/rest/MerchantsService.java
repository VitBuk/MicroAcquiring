package lv.lpb.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import lv.lpb.domain.Merchant;
import lv.lpb.database.Merchants;

@Path("/merchants")
public class MerchantsService {
    
    @GET
    @Path("/{merchantId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMerchant(@PathParam("merchantId") String id) {
        Long merchantId = Long.parseLong(id);
        Merchant merchant = Merchants.getById(merchantId);
        
        if (merchant == null) {
            return Response.status(404).entity("Merchant is not exist").build();
        }
        
        return Response.ok(merchant).build();
    }
    
    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(Merchant merchant) {
        Merchants.add(merchant);
        
        return Response.ok(merchant).build();
    }
}
