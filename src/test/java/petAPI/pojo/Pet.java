package petAPI.pojo;

import java.util.List;

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

    public Category getCategory() {
        return category;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getPhotoUrls() {
        return photoUrls;
    }

    public String getStatus() {
        return status;
    }

    public List<Tag> getTags() {
        return tags;
    }
}
