package userAPI.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponseBody {
    private Integer code;
    private String type;
    private String message;
}