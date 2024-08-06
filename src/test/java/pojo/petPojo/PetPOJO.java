package pojo.petPojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Builder
@JsonInclude(NON_NULL)
public class PetPOJO {

    Integer id;
    CategoryPOJO category;
    String name;
    List<String> photoUrls;
    List<TagPOJO> tags;
    String status;
}