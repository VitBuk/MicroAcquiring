package lv.lpb.services;

import java.math.BigDecimal;
import java.util.Map;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import lv.lpb.domain.Currency;

public class ReportSender implements EventSender{
    @Inject
    private Event<String> event;

    @Override
    public void send(Map<Currency, BigDecimal> reportAmount) {
        String message = "Report: " + reportAmount;
        event.fire(message);
    }
}
