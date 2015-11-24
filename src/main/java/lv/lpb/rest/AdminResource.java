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
import lv.lpb.database.MerchantCollectionDAO;
import lv.lpb.database.Transactions;
import lv.lpb.database.TransactionsManager;
import lv.lpb.domain.Exporter;
import lv.lpb.domain.Merchant;
import lv.lpb.domain.Transaction;
import lv.lpb.rest.params.MerchantFilterParams;
import lv.lpb.rest.params.PageParams;
import lv.lpb.rest.params.TransactionFilterParams;

@Path("/admin")
public class AdminResource {
    
     private MerchantCollectionDAO merchantCollectionDAO = new MerchantCollectionDAO().getInstance();
    
    @GET
    @Path("/merchants")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMerchants(
            @BeanParam PageParams pageParams, 
            @BeanParam MerchantFilterParams filterParams) {
        
        Map<String, Object> filterParamsMap = new HashMap<>();
        filterParamsMap.put(MerchantFilterParams.ID, filterParams.merchantId);
        filterParamsMap.put(MerchantFilterParams.STATUS, filterParams.status);
        
        Map<String, Object> pageParamsMap = new HashMap<>();
        pageParamsMap.put(PageParams.SORT, pageParams.sortParams);
        pageParamsMap.put(PageParams.ORDER, pageParams.order);
        pageParamsMap.put(PageParams.OFFSET, pageParams.offset);
        pageParamsMap.put(PageParams.LIMIT, pageParams.limit);
        
        List<Merchant> merchants = merchantCollectionDAO.getByParams(filterParamsMap, pageParamsMap);
        
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
        merchantCollectionDAO.create(merchant);
        
        return Response.ok(merchant).build();
    }
            
    @PUT
    @Path("merchants/{merchantId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response switchOffMerchant(@PathParam("merchantId") Long id, Merchant.Status status) {
        
        Merchant merchant = merchantCollectionDAO.get(id);
        merchant.setStatus(status);
        
        return Response.ok(merchant).build();
    } 
    
    @GET
    @Path("/transactions")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTransactions(
            @BeanParam PageParams pageParams,
            @BeanParam TransactionFilterParams filterParams) {
        Map<String, Object> filterParamsMap = new HashMap<>();
        filterParamsMap.put(TransactionFilterParams.ID, filterParams.transactionId);
        filterParamsMap.put(TransactionFilterParams.MERCHANT_ID, filterParams.merchantId);
        filterParamsMap.put(TransactionFilterParams.CURRENCY, filterParams.currency);
        filterParamsMap.put(TransactionFilterParams.STATUS, filterParams.status);
        filterParamsMap.put(TransactionFilterParams.INIT_DATE, filterParams.initDate);
        
        List<Transaction> transactions = TransactionsManager.getTransactions
        (filterParamsMap, pageParams.sortParams, pageParams.order, Integer.valueOf(pageParams.offset), Integer.valueOf(pageParams.limit));
        
        if (transactions.isEmpty()) {
            return Response.status(204).entity(Errors.TRANS_ZERO).build();
        }
        
        return Response.ok(transactions).build();
    }
    
    @GET
    @Path("/merchants/export")
    @Produces(MediaType.APPLICATION_JSON)
    public Response exportMerchants() {
        List<Merchant> merchants = merchantCollectionDAO.getAll();
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
