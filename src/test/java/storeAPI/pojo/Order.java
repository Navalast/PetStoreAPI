package storeAPI.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
@Getter
@Builder
public class Order {
    Integer id;
    Integer petId;
    Integer quantity;
    Date shipDate;
    String status;
    Boolean complete;
}