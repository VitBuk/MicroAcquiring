package lv.lpb.rest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.BeanParam;
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
import lv.lpb.database.TransactionsManager;
import lv.lpb.domain.Merchant;
import lv.lpb.domain.Transaction;
import lv.lpb.domain.CancelInfo;
import lv.lpb.rest.params.PageParams;
import lv.lpb.rest.params.TransactionFilterParams;

@Path("/transactions")
public class TransactionsResource {
    
    @GET
    @Path("/merchants/{merchantId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMerchantTransactions(
           @PathParam("merchantId") Long id, 
           @BeanParam PageParams pageParams, 
           @BeanParam TransactionFilterParams filterParams) { 
        
        Merchant merchant = Merchants.getById(id);
        Map<String,Object> filterParamsMap = new HashMap<String, Object>();
        filterParamsMap.put(TransactionFilterParams.MERCHANT_ID, id);
        filterParamsMap.put(TransactionFilterParams.ID, filterParams.transactionId);
        filterParamsMap.put(TransactionFilterParams.CURRENCY, filterParams.currency);
        filterParamsMap.put(TransactionFilterParams.STATUS, filterParams.status);
        filterParamsMap.put(TransactionFilterParams.INIT_DATE, filterParams.initDate);
        
        System.out.println(filterParamsMap.toString());
            
        List<Transaction> transactions = TransactionsManager.getTransactions
        (filterParamsMap, pageParams.sortParams, pageParams.order, pageParams.offset, pageParams.limit);

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
    public Response add(@PathParam("merchantId") Long id, Transaction transaction) {
        Merchant merchant = Merchants.getById(id);
        
        if (merchant.getStatus() == Merchant.Status.INACTIVE) {
            return Response.status(404).entity(Errors.MERCH_INACTIVE).build();
        }
        if (!merchant.allowedCurrency(transaction.getCurrency())) {
            return Response.status(400).entity(Errors.UNALLOWED_CURRENCY).build();
        }

        transaction.setMerchantId(id);
        transaction.setInitDate(LocalDate.now());
        Transactions.add(transaction);

        return Response.ok(transaction).build();
    }
    
    @PUT
    @Path("merchants/{merchantId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response cancelTransaction(@PathParam("merchantId") Long id, CancelInfo cancelInfo) {
        Transaction transaction = Transactions.getById(cancelInfo.getTransactionId());
        
        if(transaction.getStatus() == Transaction.Status.CLOSE) {
            return Response.status(400).entity(Errors.CANCEL_CLOSED).build();
        }
        if(transaction.getStatus() == Transaction.Status.CANCEL) {
            return Response.status(400).entity(Errors.CANCEL_CANCELED).build();
        }
        
        if (transaction.getAmount().compareTo(cancelInfo.getAmount()) == -1) {
            return Response.status(400).entity(Errors.CANCEL_LIMIT_EXCESS).build();
        }
        if (cancelInfo.getAmount() == BigDecimal.ZERO) {
            return Response.status(400).entity(Errors.CANCEL_ZERO).build();
        }
        
        if (transaction.getCurrency() != cancelInfo.getCurrency()) {
            return Response.status(400).entity(Errors.CANCEL_WRONG_CURRENCY).build();
        }
        
        if (Period.between(transaction.getInitDate(), LocalDate.now()).getDays() > 3) {
            return Response.status(400).entity(Errors.CANCEL_OVERDUE).build();
        }
        
        transaction.setAmount(transaction.getAmount().subtract(cancelInfo.getAmount()));
        
        if(transaction.getAmount().compareTo(BigDecimal.ZERO) == 0) {
            transaction.setStatus(Transaction.Status.CANCEL);
        } else {
            transaction.setStatus(Transaction.Status.CANCEL_PART);
        }
        
        transaction.setUpdated(LocalDate.now());
           
        return Response.ok(transaction).build();
    }
}
