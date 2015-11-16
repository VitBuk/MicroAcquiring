package lv.lpb.rest;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import lv.lpb.database.Merchants;
import lv.lpb.database.Transactions;
import lv.lpb.domain.Merchant;
import lv.lpb.domain.Transaction;

@Path("/admin")
public class AdminService {
    
    @GET
    @Path("/merchants")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMerchants() {
        List<Merchant> merchants = Merchants.getMerchants();
        
        if (merchants.isEmpty()) {
            return Response.status(204).entity(Errors.MERCHS_ZERO).build();
        }
        
        return Response.ok(merchants).build();
    }
    
    @POST
    @Path("/merchants")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addMerchant(Merchant merchant) {
        // id produces by database\
        merchant.setStatus(Merchant.Status.ACTIVE);
        Merchants.add(merchant);
        
        return Response.ok(merchant).build();
    }
            
    @PUT
    @Path("merchants/{merchantId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response switchOffMerchant(@PathParam("merchantId") String id, Merchant.Status status) {
        
        Merchant merchant = Merchants.getById(Long.parseLong(id));
        merchant.setStatus(status);
        
        return Response.ok(merchant).build();
    } 
    
    @GET
    @Path("/transactions")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTransactions() {
        List<Transaction> transactions = Transactions.getTransactions();
        
        if (transactions.isEmpty()) {
            return Response.status(204).entity(Errors.TRANS_ZERO).build();
        }
        
        return Response.ok(transactions).build();
    }
}
