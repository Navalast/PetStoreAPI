package pojo.petPojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.io.File;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Builder
@JsonInclude(NON_NULL)
public class UploadImagePOJO {

    Integer petId;
    String additionalMetadata;
    File file;
}
