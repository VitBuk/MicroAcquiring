package lv.lpb.services;

import java.time.LocalDateTime;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;
import lv.lpb.beans.BatchBean;
import lv.lpb.database.BatchCollectionDAO;
import lv.lpb.database.DAOQualifier;
import lv.lpb.database.MerchantCollectionDAO;
import lv.lpb.database.TransactionCollectionDAO;
import lv.lpb.domain.Batch;
import lv.lpb.domain.Merchant;
import lv.lpb.domain.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
@InterceptorQualifier
public class BatchService {

    private static final Logger log = LoggerFactory.getLogger(BatchService.class);
    
    private BatchCollectionDAO batchDAO;
    private TransactionCollectionDAO transactionDAO;
    private MerchantCollectionDAO merchantDAO;
    private BatchBean batchBean;

    public BatchService() {
    }

    @Inject
    public BatchService(BatchCollectionDAO batchDAO,
            @DAOQualifier(daoType = DAOQualifier.DaoType.TRAN) TransactionCollectionDAO transactionDAO,
            @DAOQualifier(daoType = DAOQualifier.DaoType.MERCH) MerchantCollectionDAO merchantDAO, BatchBean batchBean) {
        this.batchDAO = batchDAO;
        this.transactionDAO = transactionDAO;
        this.merchantDAO = merchantDAO;
        this.batchBean = batchBean;
    }

    @Schedule(dayOfWeek = "*", hour = "0", minute = "0", second = "1")
    public void closeBatch() {
        for (Merchant merchant : merchantDAO.getAll()) {
            create(merchant.getId());
        }
    }
    
    private Batch create(Long merchantId) {
        LocalDateTime batchDay = LocalDateTime.now().minusDays(1L);
        Batch batch = batchDAO.create(new Batch(merchantId, batchDay));
        batchBean.persist(batch);
        
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
        batchBean.update(batch);
        log.trace("Batch={} for merchant={} ", batch, merchantId);
        return batch;
    }
}
