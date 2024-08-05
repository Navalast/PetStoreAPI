package userAPI.pojo;

public class UserResponseBody {
    private Integer code;
    private String type;
    private String message;

    public UserResponseBody(Integer code, String message, String type) {
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
