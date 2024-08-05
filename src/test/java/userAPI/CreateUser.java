package userAPI;

import userAPI.pojo.User;

public class CreateUser {
    public static User createUserObject(int id, String username, String firstName, String lastName, String email, String password, String phone, int userStatus) {
        return new User(id, username, firstName, lastName, email, password, phone, userStatus);
    }
}