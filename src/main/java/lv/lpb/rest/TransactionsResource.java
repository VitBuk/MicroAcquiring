package lv.lpb.rest;

import lv.lpb.rest.errorHandling.Errors;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
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
import lv.lpb.businessLogic.TransactionsService;
import lv.lpb.database.MerchantCollectionDAO;
import lv.lpb.database.TransactionCollectionDAO;
import lv.lpb.domain.Merchant;
import lv.lpb.domain.Transaction;
import lv.lpb.domain.CancelInfo;
import lv.lpb.rest.errorHandling.AppException;
import lv.lpb.rest.params.PageParams;
import lv.lpb.rest.params.TransactionFilterParams;

@Path("/transactions")
public class TransactionsResource {

    private MerchantCollectionDAO merchantDAO;

    private TransactionCollectionDAO transactionDAO;
    
    private TransactionsService transactionsService;

    @Inject
    public TransactionsResource(MerchantCollectionDAO merchantDAO,
            TransactionCollectionDAO transactionDAO, TransactionsService transactionsService) {
        this.merchantDAO = merchantDAO;
        this.transactionDAO = transactionDAO;
        this.transactionsService = transactionsService;
    }

    @GET
    @Path("/merchants/{merchantId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMerchantTransactions(
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

        List<Transaction> transactions = transactionDAO.getByParams(filterParamsMap, pageParamsMap);

        if (merchantDAO.get(id) == null) {
            throw new AppException(Response.Status.BAD_REQUEST.getStatusCode() ,Errors.MERCH_NOT_EXIST);
        }

        return Response.ok(transactions).build();
    }

    @POST
    @Path("/merchants/{merchantId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(@PathParam("merchantId") Long id, Transaction transaction) {
        Merchant merchant = merchantDAO.get(id);

        if (merchant.getStatus() == Merchant.Status.INACTIVE) {
            throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), Errors.MERCH_INACTIVE);
        }
        if (!merchant.allowedCurrency(transaction.getCurrency())) {
            throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), Errors.UNALLOWED_CURRENCY);
        }

        transaction.setMerchantId(id);
        transaction.setCreated(LocalDate.now());
        transactionDAO.create(transaction);

        return Response.ok(transaction).build();
    }

    @PUT
    @Path("merchants/{merchantId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response cancelTransaction(@PathParam("merchantId") Long id, CancelInfo cancelInfo) {
        Transaction transaction = transactionDAO.get(cancelInfo.getTransactionId());

        if (transaction.getStatus() == Transaction.Status.CLOSE) {
            throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), Errors.CANCEL_CLOSED);
        }
        if (transaction.getStatus() == Transaction.Status.CANCEL) {
            throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), Errors.CANCEL_CANCELED);
        }

        if (transaction.getAmount().compareTo(cancelInfo.getAmount()) == -1) {
            throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), Errors.CANCEL_LIMIT_EXCESS);
            
        }
        if (cancelInfo.getAmount() == BigDecimal.ZERO) {
            throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), Errors.CANCEL_ZERO);
        }

        if (transaction.getCurrency() != cancelInfo.getCurrency()) {
            throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), Errors.CANCEL_WRONG_CURRENCY);
        }

        if (Period.between(transaction.getCreated(), LocalDate.now()).getDays() > 3) {
            throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), Errors.CANCEL_OVERDUE);
        }

        transaction = transactionsService.cancel(transaction, cancelInfo);
        transactionDAO.update(transaction);

        return Response.ok(transaction).build();
    }
}
