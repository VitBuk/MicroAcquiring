package lv.lpb.services;

import javax.enterprise.event.Event;
import javax.inject.Inject;

public class ReportSender implements EventSender{
    @Inject
    private Event<String> event;

    @Override
    public void send(String message) {
        event.fire(message);
    }
}
