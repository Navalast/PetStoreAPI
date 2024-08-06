package pojo.userPojo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponseBodyPOJO {

    Integer code;
    String type;
    String message;
}