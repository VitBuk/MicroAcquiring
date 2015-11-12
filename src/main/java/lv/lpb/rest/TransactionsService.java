package lv.lpb.rest;

import java.math.BigDecimal;
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
import lv.lpb.domain.Merchant;
import lv.lpb.domain.Transaction;
import lv.lpb.database.Transactions;
import lv.lpb.domain.CancelInfo;
import lv.lpb.domain.TransactionStatus;

@Path("/transactions")
public class TransactionsService {
    
    @GET
    @Path("/merchant/{merchantId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMerchantHistory(@PathParam("merchantId") String id) {
        Long merchantId = Long.parseLong(id);
        Merchant merchant = Merchants.getById(merchantId);
        List<Transaction> transactions = Transactions.getByMerchant(merchant);
        
        if (transactions.isEmpty()) {
            if (merchant == null) {
                return Response.status(404).entity("Merchant is not exist").build();
            } 
            
            return Response.status(404).entity("This merchant has not transactions").build();
        }
        
        return Response.ok(transactions).build();
    }
    
    @PUT
    @Path("{transactionId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response cancelTransaction(CancelInfo cancelInfo) {
        Transaction transaction = Transactions.getById(cancelInfo.getTransactionId());
        
        if(transaction.getTransactionStatus() == TransactionStatus.CLOSE) {
            return Response.status(400).entity("You cant cancel closed transaction").build();
        }
        if(transaction.getTransactionStatus() == TransactionStatus.CANCEL) {
            return Response.status(400).entity("You cant cancel canceled transaction").build();
        }
        
        if (transaction.getAmount().compareTo(cancelInfo.getAmount()) == -1) {
            return Response.status(400).entity("Cancelable amount bigger than transaction amount").build();
        }
        if (cancelInfo.getAmount() == BigDecimal.ZERO) {
            return Response.status(400).entity("You wanna cancel transaction for 0 amount, lol").build();
        }
        
        Merchant merchant = transaction.getMerchant();
        if (!merchant.allowedCurrency(cancelInfo.getCurrency())) {
            return Response.status(400).entity("Wrong currency").build();
        }
        
        transaction.setAmount(transaction.getAmount().subtract(cancelInfo.getAmount()));
        
        if(transaction.getAmount().compareTo(BigDecimal.ZERO) == 0) {
            transaction.setTransactionStatus(TransactionStatus.CANCEL);
        } else {
            transaction.setTransactionStatus(TransactionStatus.CANCEL_PART);
        }
        
        return Response.ok(transaction).build();
    }
    
    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(Transaction transaction) {
        Merchant merchant = transaction.getMerchant();
        if (!merchant.allowedCurrency(transaction.getCurrency())) {
            return Response.status(400).entity("That type of currency not allowed").build();
        }
        
        Transactions.add(transaction);
                
        return Response.ok(transaction).build();
    }
}
