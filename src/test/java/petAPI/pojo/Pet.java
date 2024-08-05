package petAPI.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Builder
@JsonInclude(NON_NULL)
public class Pet {

    Integer id;
    Category category;
    String name;
    List<String> photoUrls;
    List<Tag> tags;
    String status;
}