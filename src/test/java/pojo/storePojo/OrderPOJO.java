package pojo.storePojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.math.BigInteger;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Jacksonized
@JsonInclude(NON_NULL)
@Getter
@Builder
public class OrderPOJO {

    BigInteger id;
    BigInteger petId;
    BigInteger quantity;
    Date shipDate;
    String status;
    Boolean complete;
}