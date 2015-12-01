package lv.lpb.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.Response;
import lv.lpb.database.DAOQualifier;
import lv.lpb.database.MerchantCollectionDAO;
import lv.lpb.database.TransactionCollectionDAO;
import lv.lpb.domain.CancelInfo;
import lv.lpb.domain.Merchant;
import lv.lpb.domain.Transaction;
import lv.lpb.rest.errorHandling.AppException;
import lv.lpb.rest.errorHandling.Errors;
import lv.lpb.rest.params.TransactionFilterParams;

@ApplicationScoped
@Named("TransactionsService_CDI")
public class TransactionsService {

    private MerchantCollectionDAO merchantDAO;
    private TransactionCollectionDAO transactionDAO;

    public TransactionsService() {
    }

    @Inject
    public TransactionsService(@Named("Transaction_CDI") TransactionCollectionDAO transactionDAO, 
            @DAOQualifier(daoType = DAOQualifier.DaoType.EJB) MerchantCollectionDAO merchantDAO) {
        this.transactionDAO = transactionDAO;
        this.merchantDAO = merchantDAO;
    }

    public Transaction create(Long merchantId, Transaction transaction) {
        Merchant merchant = merchantDAO.get(merchantId);

        if (merchant.getStatus() == Merchant.Status.INACTIVE) {
            throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), Errors.MERCH_INACTIVE);
        }
        if (!merchant.allowedCurrency(transaction.getCurrency())) {
            throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), Errors.UNALLOWED_CURRENCY);
        }

        transaction.setMerchantId(merchantId);
        transaction.setCreated(LocalDate.now());
        transactionDAO.create(transaction);

        return transaction;
    }

    public Transaction cancel(CancelInfo cancelInfo) {
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

        transactionDAO.update(transaction);

        return transaction;
    }

    public List<Transaction> getByMerchant(Map<String, Object> filterParams, Map<String, Object> pageParams) {
        List<Transaction> transactions = transactionDAO.getByParams(filterParams, pageParams);

        if (merchantDAO.get((Long) filterParams.get(TransactionFilterParams.MERCHANT_ID)) == null) {
            throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), Errors.MERCH_NOT_EXIST);
        }

        return transactions;
    }
}
