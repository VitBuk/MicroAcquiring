package lv.lpb.rest;

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
import lv.lpb.services.TransactionsService;
import lv.lpb.domain.Transaction;
import lv.lpb.domain.CancelInfo;
import lv.lpb.rest.params.PageParams;
import lv.lpb.rest.params.TransactionFilterParams;
import lv.lpb.services.ServiceQualifier;
import lv.lpb.services.ServiceQualifier.ServiceType;

@Path("/transactions")
public class TransactionsResource {

    private TransactionsService transactionsService;

    @Inject
    public TransactionsResource(@ServiceQualifier(serviceType = ServiceType.TRAN) TransactionsService transactionsService) {
        this.transactionsService = transactionsService;
    }

    @GET
    @Path("/merchants/{merchantId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByMerchant(
            @PathParam("merchantId") Long id,
            @BeanParam PageParams pageParams,
            @BeanParam TransactionFilterParams filterParams) {

        Map<String, Object> filterParamsMap = new HashMap<>();
        filterParamsMap.put(TransactionFilterParams.MERCHANT_ID, id);
        filterParamsMap.put(TransactionFilterParams.ID, filterParams.transactionId);
        filterParamsMap.put(TransactionFilterParams.CURRENCY, filterParams.currency);
        filterParamsMap.put(TransactionFilterParams.STATUS, filterParams.status);
        filterParamsMap.put(TransactionFilterParams.INIT_DATE, filterParams.initDate);

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
