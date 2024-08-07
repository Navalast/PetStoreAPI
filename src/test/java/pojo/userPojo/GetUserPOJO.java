package pojo.userPojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@SuperBuilder
@JsonInclude(NON_NULL)
@Data
@NoArgsConstructor
public class GetUserPOJO {

    String username;
}