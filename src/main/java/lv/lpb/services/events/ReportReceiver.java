package lv.lpb.services.events;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequestScoped
public class ReportReceiver {
    
    private static final Logger log = LoggerFactory.getLogger(ReportReceiver.class);
    
    private static final String emptyReport = "Empty report";

    void receive(@Observes String report) {
        log.trace(report==null || report.isEmpty() ? emptyReport : report);
    }
}
