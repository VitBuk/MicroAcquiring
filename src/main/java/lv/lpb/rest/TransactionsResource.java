package lv.lpb.rest;

import lv.lpb.rest.params.CancelInfo;
import java.util.HashMap;
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
import lv.lpb.database.DAO;
import lv.lpb.database.MerchantDAO;
import lv.lpb.services.TransactionsService;
import lv.lpb.domain.Transaction;
import lv.lpb.domain.Merchant;
import lv.lpb.rest.params.PageParams;
import lv.lpb.rest.params.TransactionFilterParams;

@Path("/transactions")
public class TransactionsResource {

    private TransactionsService transactionsService;
    private MerchantDAO merchantDAO;

    @Inject
    public TransactionsResource(TransactionsService transactionsService,
            @DAO MerchantDAO merchantDAO) {
        this.transactionsService = transactionsService;
        this.merchantDAO = merchantDAO;
    }

    @GET
    @Path("/merchants/{merchantId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByMerchant(
            @PathParam("merchantId") Long id,
            @BeanParam PageParams pageParams,
            @BeanParam TransactionFilterParams filterParams) {

        Merchant merchant = merchantDAO.get(id);

        Map<String, Object> filterParamsMap = new HashMap<>();
        filterParamsMap.put(TransactionFilterParams.MERCHANT, merchant);
        filterParamsMap.put(TransactionFilterParams.ID, filterParams.transactionId);
        filterParamsMap.put(TransactionFilterParams.CURRENCY, filterParams.currency);
        filterParamsMap.put(TransactionFilterParams.STATUS, filterParams.status);
        filterParamsMap.put(TransactionFilterParams.CREATED, filterParams.created);

        Map<String, Object> pageParamsMap = new HashMap<>();
        pageParamsMap.put(PageParams.SORT, pageParams.sort);
        pageParamsMap.put(PageParams.ORDER, pageParams.order);
        pageParamsMap.put(PageParams.OFFSET, pageParams.offset);
        pageParamsMap.put(PageParams.LIMIT, pageParams.limit);

        return Response.ok(transactionsService.getByMerchant(filterParamsMap, pageParamsMap)).build();
    }

    @POST
    @Path("/merchants/{merchantId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(@PathParam("merchantId") Long id, Transaction transaction) {
        return Response.ok(transactionsService.create(id, transaction)).build();
    }

    @PUT
    @Path("merchants/{merchantId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response cancel(@PathParam("merchantId") Long id, CancelInfo cancelInfo) {
        return Response.ok(transactionsService.cancel(cancelInfo)).build();
    }
}
