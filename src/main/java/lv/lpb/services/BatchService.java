package lv.lpb.services;

import java.time.LocalDateTime;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;
import lv.lpb.database.BatchDAO;
import lv.lpb.database.DAOQualifier;
import lv.lpb.database.DAOQualifier.DaoType;
import lv.lpb.database.MerchantDAO;
import lv.lpb.database.TransactionDAO;
import lv.lpb.domain.Batch;
import lv.lpb.domain.Merchant;
import lv.lpb.domain.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
@InterceptorQualifier
public class BatchService {

    private static final Logger log = LoggerFactory.getLogger(BatchService.class);

    private BatchDAO batchDAO;
    private TransactionDAO transactionDAO;
    private MerchantDAO merchantDAO;

    public BatchService() {
    }

    @Inject
    public BatchService(@DAOQualifier(daoType = DaoType.DATABASE) BatchDAO batchDAO,
            @DAOQualifier(daoType = DaoType.DATABASE) TransactionDAO transactionDAO,
            @DAOQualifier(daoType = DaoType.DATABASE) MerchantDAO merchantDAO) {
        this.batchDAO = batchDAO;
        this.transactionDAO = transactionDAO;
        this.merchantDAO = merchantDAO;
    }

    @Schedule(dayOfWeek = "*", hour = "0", minute = "0", second = "1")
    public void closeBatch() {
        for (Merchant merchant : merchantDAO.getAll()) {
            create(merchant.getId());
        }
    }

    private Batch create(Long merchantId) {
        LocalDateTime batchDay = LocalDateTime.now().minusDays(1L);
        Merchant merchant = merchantDAO.get(merchantId);
        Batch batch = batchDAO.create(new Batch(merchant, batchDay));
        batchDAO.create(batch);

        for (Transaction transaction : transactionDAO.getByMerchantId(merchantId)) {
            if (transaction.getStatus() == Transaction.Status.DEPOSITED
                    && !transaction.inBatch()
                    && (batchDay.isAfter(transaction.getCreated()) || batchDay.isEqual(transaction.getCreated()))) {
                transaction.setStatus(Transaction.Status.PROCESSED);
                transaction.setBatchId(batch.getId());
                batch.add(transaction);
            }
        }

        batch = batchDAO.update(batch);
        log.trace("Batch={} for merchant={} ", batch, merchantId);
        return batch;
    }
}
