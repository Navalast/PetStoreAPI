package pojo.userPojo;

import lombok.Getter;

@Getter
public class UpdateUserPOJO extends GetUserPOJO {

    Integer id;
    String firstName;
    String lastName;
    String email;
    String password;
    String phone;
    Integer userStatus;

    public UpdateUserPOJO(String username, Integer id, String firstName, String lastName, String email, String password, String phone, Integer userStatus) {

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