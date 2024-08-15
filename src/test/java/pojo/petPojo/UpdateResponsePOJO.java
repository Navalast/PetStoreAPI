package pojo.petPojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.math.BigInteger;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Jacksonized
@Getter
@Builder
@JsonInclude(NON_NULL)
public class UpdateResponsePOJO {

    BigInteger code;
    String type;
    String message;
}