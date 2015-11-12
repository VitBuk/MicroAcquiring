package lv.lpb.domain;

public enum TransactionStatus {
    INIT("Initialized"),
    CANCEL("Canceled"),
    CANCEL_PART("Canceled in part"),
    CLOSE("Closed");
    
    private String name;
    
    TransactionStatus(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
