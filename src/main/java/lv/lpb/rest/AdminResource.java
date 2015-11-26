package lv.lpb.rest;

import lv.lpb.rest.errorHandling.Errors;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
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
import lv.lpb.database.TransactionCollectionDAO;
import lv.lpb.domain.Exporter;
import lv.lpb.domain.Merchant;
import lv.lpb.domain.Transaction;
import lv.lpb.rest.errorHandling.AppException;
import lv.lpb.rest.params.MerchantFilterParams;
import lv.lpb.rest.params.PageParams;
import lv.lpb.rest.params.TransactionFilterParams;

@Path("/admin")
public class AdminResource {

    private MerchantCollectionDAO merchantDAO;
    private TransactionCollectionDAO transactionDAO;

    @Inject
    public AdminResource(MerchantCollectionDAO merchantDAO, TransactionCollectionDAO transactionDAO) {
        this.merchantDAO = merchantDAO;
        this.transactionDAO = transactionDAO;
    }

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
        pageParamsMap.put(PageParams.SORT, pageParams.sort);
        pageParamsMap.put(PageParams.ORDER, pageParams.order);
        pageParamsMap.put(PageParams.OFFSET, pageParams.offset);
        pageParamsMap.put(PageParams.LIMIT, pageParams.limit);

        List<Merchant> merchants = merchantDAO.getByParams(filterParamsMap, pageParamsMap);

        if (merchants.isEmpty()) {
            throw new AppException(Response.Status.NO_CONTENT.getStatusCode(), Errors.MERCHS_ZERO);
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
        merchantDAO.create(merchant);

        return Response.ok(merchant).build();
    }

    @PUT
    @Path("merchants/{merchantId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response switchOffMerchant(@PathParam("merchantId") Long id, Merchant.Status status) {

        Merchant merchant = merchantDAO.get(id);
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

        Map<String, Object> pageParamsMap = new HashMap<>();
        pageParamsMap.put(PageParams.SORT, pageParams.sort);
        pageParamsMap.put(PageParams.ORDER, pageParams.order);
        pageParamsMap.put(PageParams.OFFSET, pageParams.offset);
        pageParamsMap.put(PageParams.LIMIT, pageParams.limit);

        List<Transaction> transactions = transactionDAO.getByParams(filterParamsMap, pageParamsMap);

        if (transactions.isEmpty()) {
            throw new AppException(Response.Status.NO_CONTENT.getStatusCode(), Errors.TRANS_ZERO);
        }

        return Response.ok(transactions).build();
    }

    @GET
    @Path("/merchants/export")
    @Produces(MediaType.APPLICATION_JSON)
    public Response exportMerchants() {
        List<Merchant> merchants = merchantDAO.getAll();
        Exporter exporter = new Exporter(merchants);

        return Response.ok(exporter).build();
    }

    @GET
    @Path("/transactions/export")
    @Produces(MediaType.APPLICATION_JSON)
    public Response exportTransactions() {
        List<Transaction> transactions = transactionDAO.getAll();
        Exporter exporter = new Exporter(transactions);

        return Response.ok(exporter).build();
    }
}
