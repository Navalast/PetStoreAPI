package userAPI.pojo;

import lombok.Getter;

@Getter
public class UpdateUser extends GetUser {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private Integer userStatus;

    public UpdateUser(String username, Integer id, String firstName, String lastName, String email, String password, String phone, Integer userStatus) {
        super(username);
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.userStatus = userStatus;
    }
}