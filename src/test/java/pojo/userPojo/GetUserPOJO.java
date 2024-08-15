package pojo.userPojo;

import com.fasterxml.jackson.annotation.JacksonAnnotation;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.testng.annotations.Ignore;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Jacksonized
@SuperBuilder
@JsonInclude(NON_NULL)
@Data
@NoArgsConstructor
public class GetUserPOJO {

    String username;
}