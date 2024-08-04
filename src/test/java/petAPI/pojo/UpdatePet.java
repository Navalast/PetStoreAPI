package petAPI.pojo;

public class UpdatePet {
    private Integer id;
    private String name;
    private String status;

    public UpdatePet(Integer id, String name, String status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }
}
