package lv.lpb.businessLogic;

import java.util.List;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.Response;
import lv.lpb.database.MerchantCollectionDAO;
import lv.lpb.database.TransactionCollectionDAO;
import lv.lpb.domain.Exporter;
import lv.lpb.domain.Merchant;
import lv.lpb.domain.Transaction;
import lv.lpb.rest.errorHandling.AppException;
import lv.lpb.rest.errorHandling.Errors;

@Named("AdminService_CDI")
@RequestScoped
public class AdminService {

    private MerchantCollectionDAO merchantDAO;
    private TransactionCollectionDAO transactionDAO;

    public AdminService() {
    }

    @Inject
    public AdminService(@Named("Merchant_CDI") MerchantCollectionDAO merchantDAO,
                        @Named("Transaction_CDI") TransactionCollectionDAO transactionDAO) {
        this.merchantDAO = merchantDAO;
        this.transactionDAO = transactionDAO;
    }

    public List<Merchant> getMerchants(Map<String, Object> filterParams, Map<String, Object> pageParams) {
        List<Merchant> merchants = merchantDAO.getByParams(filterParams, pageParams);

        if (merchants.isEmpty()) {
            throw new AppException(Response.Status.NO_CONTENT.getStatusCode(), Errors.MERCHS_ZERO);
        }

        return merchants;
    }

    public Merchant addMerchant(Merchant merchant) {
        merchant.setStatus(Merchant.Status.ACTIVE);
        merchantDAO.create(merchant);
        merchantDAO.get(merchant.getId());

        return merchant;
    }

    public Merchant switchOffMerchant(Long merchantId, Merchant.Status status) {
        Merchant merchant = merchantDAO.get(merchantId);
        merchant.setStatus(status);
        merchantDAO.update(merchant);

        return merchant;
    }

    public List<Transaction> getTransactions(Map<String, Object> filterParams, Map<String, Object> pageParams) {
        List<Transaction> transactions = transactionDAO.getByParams(filterParams, pageParams);

        if (transactions.isEmpty()) {
            throw new AppException(Response.Status.NO_CONTENT.getStatusCode(), Errors.TRANS_ZERO);
        }

        return transactions;
    }

    public Exporter exportMerchants() {
        List<Merchant> merchants = merchantDAO.getAll();
        Exporter exporter = new Exporter(merchants);

        return exporter;
    }

    public Exporter exportTransactions() {
        List<Transaction> transactions = transactionDAO.getAll();
        Exporter exporter = new Exporter(transactions);

        return exporter;
    }
}