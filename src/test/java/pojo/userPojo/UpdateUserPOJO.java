package pojo.userPojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigInteger;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@EqualsAndHashCode(callSuper = true)
@Getter
@SuperBuilder
@JsonInclude(NON_NULL)
@Data
@NoArgsConstructor
public class UpdateUserPOJO extends GetUserPOJO {

    BigInteger id;
    String firstName;
    String lastName;
    String email;
    String password;
    String phone;
    BigInteger userStatus;
}