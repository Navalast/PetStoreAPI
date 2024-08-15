package pojo.storePojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.math.BigInteger;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Jacksonized
@JsonInclude(NON_NULL)
@Getter
@Builder
public class DeleteResponsePOJO {

    BigInteger code;
    String type;
    String message;
}