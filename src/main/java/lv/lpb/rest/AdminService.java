package lv.lpb.rest;

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
import lv.lpb.database.MerchantsManager;
import lv.lpb.database.Transactions;
import lv.lpb.database.TransactionsManager;
import lv.lpb.domain.Exporter;
import lv.lpb.domain.Merchant;
import lv.lpb.domain.Transaction;

@Path("/admin")
public class AdminService {
    
    @GET
    @Path("/merchants")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMerchants(
            @BeanParam Merchant.PageParams pageParams, 
            @BeanParam Merchant.FilterParams filterParams) {
        
        Map<String, Object> filterParamsMap = new HashMap<>();
        filterParamsMap.put(Merchant.FilterParams.ID, filterParams.merchantId);
        filterParamsMap.put(Merchant.FilterParams.STATUS, filterParams.status);
        
        Map<String, String> pageParamsMap = new HashMap<>();
        pageParamsMap.put(Merchant.PageParams.SORT, pageParams.sortParams);
        pageParamsMap.put(Merchant.PageParams.ORDER, pageParams.order);
        pageParamsMap.put(Merchant.PageParams.OFFSET, String.valueOf(pageParams.offset));
        pageParamsMap.put(Merchant.PageParams.LIMIT, String.valueOf(pageParams.limit));
        
        System.out.println("filterParams: " + filterParams);
        List<Merchant> merchants = MerchantsManager.getMerchants(filterParamsMap, pageParamsMap);
        
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
        // id produces by database
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
    public Response getTransactions(
            @BeanParam Transaction.PageParams pageParams,
            @BeanParam Transaction.FilterParams filterParams) {
        Map<String, Object> filterParamsMap = new HashMap<>();
        filterParamsMap.put(Transaction.FilterParams.ID, filterParams.transactionId);
        filterParamsMap.put(Transaction.FilterParams.MERCHANT_ID, filterParams.merchantId);
        filterParamsMap.put(Transaction.FilterParams.CURRENCY, filterParams.currency);
        filterParamsMap.put(Transaction.FilterParams.STATUS, filterParams.status);
        filterParamsMap.put(Transaction.FilterParams.INIT_DATE, filterParams.initDate);
        
        List<Transaction> transactions = TransactionsManager.getTransactions
        (filterParamsMap, pageParams.sortParams, pageParams.order, pageParams.offset, pageParams.limit);
        
        if (transactions.isEmpty()) {
            return Response.status(204).entity(Errors.TRANS_ZERO).build();
        }
        
        return Response.ok(transactions).build();
    }
    
    @GET
    @Path("/merchants/export")
    @Produces(MediaType.APPLICATION_JSON)
    public Response exportMerchants() {
        List<Merchant> merchants = Merchants.getMerchants();
        Exporter exporter = new Exporter(merchants);
        
        return Response.ok(exporter).build();
    }
    
    @GET
    @Path("/transactions/export")
    @Produces(MediaType.APPLICATION_JSON)
    public Response exportTransactions() {
        List<Transaction> transactions = Transactions.getTransactions();
        Exporter exporter = new Exporter(transactions);
        
        return Response.ok(exporter).build();
    }
}
