package lv.lpb.rest;

import java.math.BigDecimal;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import lv.lpb.domain.Currency;
import lv.lpb.domain.Merchant;
import lv.lpb.domain.Transaction;
import lv.lpb.domain.TransactionHistory;

@Path("/transactions")
public class TransactionsService {
    
    @GET
    @Path("/merchant/{merchantId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMerchantHistory(@PathParam("merchantId") String id) {
        Long merchantId = Long.parseLong(id);
        Merchant merchant = new Merchant(merchantId);
        TransactionHistory transactionHistory = new TransactionHistory();
        
        List<Transaction> transactions = transactionHistory.getByMerchant(merchant);
        
        if (transactions.isEmpty()) {
            //check for merchant without transactions
            
            return Response.status(404).entity("No transactions").build();
        }
        
        return Response.ok(transactions).build();
    }
    
    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(@FormParam(value = "id") Long id) {
        TransactionHistory transactionHistory = new TransactionHistory();
        Transaction transaction = new Transaction();
        transaction.setId(id);
        transaction.setAmount(BigDecimal.ZERO);
        transaction.setCurrency(Currency.EUR);
        transaction.setMerchant(new Merchant(1L));
        transactionHistory.add(transaction);
        
        return Response.ok(transaction).build();
    }

//    @POST
//    @Path("/add")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response create(@FormParam(value = "id") Long id) {
//        TransactionHistory transactionHistory = new TransactionHistory();
//        Transaction transaction = new Transaction();
//        transaction.setId(id);
//        transaction.setAmount(BigDecimal.ZERO);
//        transaction.setCurrency(Currency.EUR);
//        transaction.setMerchant(new Merchant(1L));
//        transactionHistory.add(transaction);
//        
//        return Response.ok(transaction).build();
//    }
}
