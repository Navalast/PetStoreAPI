package pojo.storePojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
@Getter
@Builder
public class DeleteResponsePOJO {

    Integer code;
    String type;
    String message;
}