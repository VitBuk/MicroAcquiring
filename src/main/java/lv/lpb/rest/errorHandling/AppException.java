package lv.lpb.rest.errorHandling;

public class AppException extends RuntimeException {
    private Integer status;
    
    public AppException(int status, String message) {
        super(message);
        this.status = status;
    }

    public AppException() {}

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
