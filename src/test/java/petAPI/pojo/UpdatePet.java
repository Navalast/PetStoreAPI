package petAPI.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdatePet {
    private Integer id;
    private String name;
    private String status;
}