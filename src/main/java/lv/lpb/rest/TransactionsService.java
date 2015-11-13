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
import lv.lpb.domain.MerchantStatus;
import lv.lpb.domain.TransactionStatus;

@Path("/transactions")
public class TransactionsService {

    @GET
    @Path("/merchants/{merchantId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMerchantHistory(@PathParam("merchantId") String id) {
        Merchant merchant = Merchants.getById(Long.parseLong(id));
        List<Transaction> transactions = Transactions.getByMerchantId(Long.parseLong(id));

        if (transactions.isEmpty()) {
            if (merchant == null) {
                return Response.status(404).entity(Errors.MERCH_NOT_EXIST).build();
            }

            return Response.status(404).entity(Errors.MERCH_HAVENT_TRAN).build();
        }

        return Response.ok(transactions).build();
    }

    @POST
    @Path("/merchants/{merchantId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(@PathParam("merchantId") String id, Transaction transaction) {
        Merchant merchant = Merchants.getById(Long.parseLong(id));
        
        if (merchant.getStatus() == MerchantStatus.INACTIVE) {
            return Response.status(404).entity(Errors.MERCH_INACTIVE).build();
        }
        if (!merchant.allowedCurrency(transaction.getCurrency())) {
            return Response.status(400).entity(Errors.UNALLOWED_CURRENCY).build();
        }

        transaction.setMerchantId(Long.parseLong(id));
        Transactions.add(transaction);

        return Response.ok(transaction).build();
    }
    
    @PUT
    @Path("merchants/{merchantId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response cancelTransaction(@PathParam("merchantId") String id, CancelInfo cancelInfo) {
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
        
        if (transaction.getCurrency() != cancelInfo.getCurrency()) {
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
    
    /* Alterntive cancelTransaction 
    @PUT
    @Path("merchants/{merchantId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response cancelTransaction(@PathParam("merchantId") String merchantId, Transaction transaction) {
        transaction.setMerchantId(Long.parseLong(merchantId));
        Transaction transactionFromDB = Transactions.getById(transaction.getId());

        if (transactionFromDB.getTransactionStatus() == TransactionStatus.CLOSE) {
            return Response.status(400).entity(Errors.CANCEL_CLOSED).build();
        }
        if (transactionFromDB.getTransactionStatus() == TransactionStatus.CANCEL) {
            return Response.status(400).entity(Errors.CANCEL_CANCELED).build();
        }

        if (transaction.getAmount().compareTo(BigDecimal.ZERO) == -1
                || transaction.getAmount().compareTo(BigDecimal.ZERO) == 0) {
            return Response.status(400).entity(Errors.CANCEL_LIMIT_EXCESS).build();
        }
        if (transactionFromDB.getAmount().compareTo(transaction.getAmount()) == 0) {
            return Response.status(400).entity(Errors.CANCEL_ZERO).build();
        }

        if (transactionFromDB.getCurrency() != transaction.getCurrency()) {
            return Response.status(400).entity(Errors.CANCEL_WRONG_CURRENCY).build();
        }

        Transactions.update(transaction);
        
        if (transaction.getAmount().compareTo(BigDecimal.ZERO) == 0) {
            transaction.setTransactionStatus(TransactionStatus.CANCEL);
        } else {
            transaction.setTransactionStatus(TransactionStatus.CANCEL_PART);
        }

        return Response.ok(transaction).build();
    }
    */
}
