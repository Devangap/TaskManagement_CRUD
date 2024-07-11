package Model;


public class ErrorModel {
    private String error;
    private String success;

    public ErrorModel(String error, String success) {
        this.error = error;
        this.success = success;
    }

    // Getters and setters
    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}

