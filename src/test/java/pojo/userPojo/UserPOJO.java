package pojo.userPojo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserPOJO {

    Integer id;
    String username;
    String firstName;
    String lastName;
    String email;
    String password;
    String phone;
    Integer userStatus;
}