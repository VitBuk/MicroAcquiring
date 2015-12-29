package lv.lpb.services;

import java.time.LocalDateTime;
import java.util.List;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;
import lv.lpb.database.BatchDAO;
import lv.lpb.database.DAOQualifier;
import lv.lpb.database.MerchantDAO;
import lv.lpb.database.TransactionDAO;
import lv.lpb.domain.Batch;
import lv.lpb.domain.Merchant;
import lv.lpb.domain.Transaction;
import lv.lpb.services.ServiceInterceptor.ProfileExecTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
@ProfileExecTime
public class BatchService {

    private static final Logger log = LoggerFactory.getLogger(BatchService.class);

    private BatchDAO batchDAO;
    private TransactionDAO transactionDAO;
    private MerchantDAO merchantDAO;

    public BatchService() {
    }

    @Inject
    public BatchService(@DAOQualifier BatchDAO batchDAO,
            @DAOQualifier TransactionDAO transactionDAO,
            @DAOQualifier MerchantDAO merchantDAO) {
        this.batchDAO = batchDAO;
        this.transactionDAO = transactionDAO;
        this.merchantDAO = merchantDAO;
    }

    @Schedule(dayOfWeek = "*", hour = "*", minute = "*", second = "30")
    public void closeBatch() {
        for (Merchant merchant : merchantDAO.getAll()) {
            create(merchant.getId());
        }
    }

    private Batch create(Long merchantId) {
        LocalDateTime batchDay = LocalDateTime.now().minusDays(1L);
        Merchant merchant = merchantDAO.get(merchantId);
        Batch batch = batchDAO.create(new Batch(merchant, batchDay));

        List<Transaction> transactions = transactionDAO.getByMerchant(merchant);
        if (transactions.isEmpty()) { 
            return null; 
        }
        
        for (Transaction transaction : transactions) {
            if (transaction.getStatus() == Transaction.Status.DEPOSITED
                    && !transaction.inBatch()
                    && (batchDay.isAfter(transaction.getCreated()) || batchDay.isEqual(transaction.getCreated()))) {
                transaction.setStatus(Transaction.Status.PROCESSED);
                transaction.setBatchId(batch.getId());
                batch.add(transaction);
            }
        }

        batch = batchDAO.update(batch);
        batch.getTransactions().get(0).getMerchant().getMerchantAgreements().stream().
                forEach(aggrement -> log.trace("MerchantAgreement={} ", aggrement.getCurrency()));
        log.trace("Batch={} for merchant={} ", batch, merchantId);
        
        return batch;
    }
}
