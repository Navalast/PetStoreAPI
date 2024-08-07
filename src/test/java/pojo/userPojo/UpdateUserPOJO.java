package pojo.userPojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@EqualsAndHashCode(callSuper = true)
@Getter
@SuperBuilder
@JsonInclude(NON_NULL)
@Data
@NoArgsConstructor
public class UpdateUserPOJO extends GetUserPOJO {

    Integer id;
    String firstName;
    String lastName;
    String email;
    String password;
    String phone;
    Integer userStatus;
}