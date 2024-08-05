package petAPI.pojo;

import lombok.Getter;

import java.util.List;

@Getter
public class Pet {
    private Integer id;
    private Category category;
    private String name;
    private List<String> photoUrls;
    private List<Tag> tags;
    private String status;

    public Pet(Category category, Integer id, String name, List<String> photoUrls, String status, List<Tag> tags) {
        this.category = category;
        this.id = id;
        this.name = name;
        this.photoUrls = photoUrls;
        this.status = status;
        this.tags = tags;
    }
}