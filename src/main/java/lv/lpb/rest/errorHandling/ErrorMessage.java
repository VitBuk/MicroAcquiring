package lv.lpb.rest.errorHandling;

public class ErrorMessage {

    private Integer status;
    private String message;

    public ErrorMessage(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public ErrorMessage(AppException exception) {
        this.status = exception.getStatus();
        this.message = exception.getMessage();
    }

    public ErrorMessage() {
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}