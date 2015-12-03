package lv.lpb.services;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;

@SessionScoped
public class ReportReceiver implements EventReceiver, Serializable {
    private String greet = "Empty report";

    void receive(@Observes String greet) {
        this.greet = greet;
    }

    @Override
    public String getGreet() {
        return greet;
    }
}
