package petAPI.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateResponse {
    private Integer code;
    private String type;
    private String message;
}