package petAPI.pojo;

public class UpdateResponse {
    private Integer code;
    private String type;
    private String message;

    public UpdateResponse(Integer code, String message, String type) {
        this.code = code;
        this.message = message;
        this.type = type;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getType() {
        return type;
    }
}
