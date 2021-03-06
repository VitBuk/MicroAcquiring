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
import lv.lpb.services.AdminService;
import lv.lpb.domain.Merchant;
import lv.lpb.rest.params.MerchantFilterParams;
import lv.lpb.rest.params.PageParams;
import lv.lpb.rest.params.TransactionFilterParams;

@Path("/admin")
@Produces(MediaType.APPLICATION_JSON)
public class AdminResource {

    private AdminService adminService;

    @Inject
    public AdminResource(AdminService adminService) {
        this.adminService = adminService;
    }

    @GET
    @Path("/merchants")
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

        return Response.ok(adminService.getMerchants(filterParamsMap, pageParamsMap)).build();
    }

    @POST
    @Path("/merchants")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addMerchant(Merchant merchant) {
        return Response.ok(adminService.addMerchant(merchant)).build();
    }

    @PUT
    @Path("merchants/{merchantId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response switchOffMerchant(@PathParam("merchantId") Long id, Merchant.Status status) {
        return Response.ok(adminService.switchOffMerchant(id, status)).build();
    }

    @GET
    @Path("/transactions")
    public Response getTransactions(
            @BeanParam PageParams pageParams,
            @BeanParam TransactionFilterParams filterParams) {
        Map<String, Object> filterParamsMap = new HashMap<>();
        filterParamsMap.put(TransactionFilterParams.ID, filterParams.transactionId);
        filterParamsMap.put(TransactionFilterParams.MERCHANT, filterParams.merchant);
        filterParamsMap.put(TransactionFilterParams.CURRENCY, filterParams.currency);
        filterParamsMap.put(TransactionFilterParams.STATUS, filterParams.status);
        filterParamsMap.put(TransactionFilterParams.CREATED, filterParams.created);

        Map<String, Object> pageParamsMap = new HashMap<>();
        pageParamsMap.put(PageParams.SORT, pageParams.sort);
        pageParamsMap.put(PageParams.ORDER, pageParams.order);
        pageParamsMap.put(PageParams.OFFSET, pageParams.offset);
        pageParamsMap.put(PageParams.LIMIT, pageParams.limit);

        return Response.ok(adminService.getTransactions(filterParamsMap, pageParamsMap)).build();
    }

    @GET
    @Path("/merchants/export")
    public Response exportMerchants() {
        return Response.ok(adminService.exportMerchants()).build();
    }

    @GET
    @Path("/transactions/export")
    public Response exportTransactions() {
        return Response.ok(adminService.exportTransactions()).build();
    }
}
