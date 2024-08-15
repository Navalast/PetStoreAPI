package pojo.userPojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.math.BigInteger;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Jacksonized
@Getter
@AllArgsConstructor
@JsonInclude(NON_NULL)
@Builder
public class UserResponseBodyPOJO {

    BigInteger code;
    String type;
    String message;
}