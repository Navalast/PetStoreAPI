package pojo.userPojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Builder
@JsonInclude(NON_NULL)
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