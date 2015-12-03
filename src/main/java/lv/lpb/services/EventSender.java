package lv.lpb.services;

import java.math.BigDecimal;
import java.util.Map;
import lv.lpb.domain.Currency;

public interface EventSender {
    void send(Map<Currency, BigDecimal> mapAmount);
}
