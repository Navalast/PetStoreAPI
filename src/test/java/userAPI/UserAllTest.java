package userAPI;

import confForTests.ResponseCode;
import confForTests.Setup;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import userAPI.pojo.*;

import static io.restassured.RestAssured.given;

public class UserAllTest extends Setup {

    private User userVictor = new User(10, "Krasavec", "Tony", "Krasavkovich", "mail@mail.ru",
            "2225", "98316516", 1);

    @Test(priority = 1)
    public void createUserTest() {
        RestAssured.responseSpecification = ResponseCode.resSpecUnique(200);

        UserResponseBody response = given()
                .body(userVictor)
                .when()
                .post("/user")
                .then()
                .extract().as(UserResponseBody.class);

        Assert.assertNotNull(response);
    }

    @Test(priority = 2)
    public void getUserLoginTest() {
        RestAssured.responseSpecification = ResponseCode.resSpecUnique(200);

        LoginUser user = new LoginUser(userVictor.getUsername(), userVictor.getPassword());
        UserResponseBody response = given()
                .body(user)
                .when()
                .get("/user/login")
                .then()
                .extract().as(UserResponseBody.class);

        Assert.assertNotNull(response);
        Assert.assertEquals(user.getUsername(), userVictor.getUsername());
    }

    @Test(priority = 3)
    public void getUserTest() {
        RestAssured.responseSpecification = ResponseCode.resSpecUnique(200);

        GetUser getUser = new GetUser(userVictor.getUsername());
        User response = given()
                .body(getUser)
                .when()
                .get("/user/" + getUser.getUsername())
                .then()
                .extract().as(User.class);

        Assert.assertEquals(response.getUsername(), getUser.getUsername());
    }

    @Test(priority = 4)
    public void updateUserTest() {
        RestAssured.responseSpecification = ResponseCode.resSpecUnique(200);

        UpdateUser updateUser = new UpdateUser(userVictor.getUsername(), userVictor.getId(), "Tony228", userVictor.getLastName(), userVictor.getEmail(),
                userVictor.getPassword(), userVictor.getPhone(), userVictor.getUserStatus());

        UserResponseBody response = given()
                .body(updateUser)
                .when()
                .put("/user/" + updateUser.getUsername())
                .then()
                .extract().as(UserResponseBody.class);

        Assert.assertNotNull(response);
        Assert.assertNotSame(updateUser.getFirstName(), userVictor.getFirstName());
    }

    @Test(priority = 5)
    public void deleteUserTest() {
        RestAssured.responseSpecification = ResponseCode.resSpecUnique(200);

        LoginUser user = new LoginUser(userVictor.getUsername(), userVictor.getPassword());

        Response response = given()
                .when()
                .delete("/user/" + user.getUsername())
                .then()
                .extract().response();

        Assert.assertNotNull(response);
    }
}