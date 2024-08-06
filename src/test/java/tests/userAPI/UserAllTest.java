package tests.userAPI;

import confForTests.ResponseCode;
import confForTests.Setup;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojo.userPojo.*;

import static io.restassured.RestAssured.given;

public class UserAllTest extends Setup {

    private UserPOJO userVictor = new UserPOJO(10, "Krasavec", "Tony", "Krasavkovich", "mail@mail.ru",
            "2225", "98316516", 1);

    @Test(priority = 1)
    public void createUserTest() {
        RestAssured.responseSpecification = ResponseCode.resSpecUnique(200);

        UserResponseBodyPOJO response = given()
                .body(userVictor)
                .when()
                .post("/user")
                .then()
                .extract().as(UserResponseBodyPOJO.class);

        Assert.assertNotNull(response);
    }

    @Test(priority = 2)
    public void getUserLoginTest() {
        RestAssured.responseSpecification = ResponseCode.resSpecUnique(200);

        LoginUserPOJO user = new LoginUserPOJO(userVictor.getUsername(), userVictor.getPassword());
        UserResponseBodyPOJO response = given()
                .body(user)
                .when()
                .get("/user/login")
                .then()
                .extract().as(UserResponseBodyPOJO.class);

        Assert.assertNotNull(response);
        Assert.assertEquals(user.getUsername(), userVictor.getUsername());
    }

    @Test(priority = 3)
    public void getUserTest() {
        RestAssured.responseSpecification = ResponseCode.resSpecUnique(200);

        GetUserPOJO getUser = new GetUserPOJO(userVictor.getUsername());
        UserPOJO response = given()
                .body(getUser)
                .when()
                .get("/user/" + getUser.getUsername())
                .then()
                .extract().as(UserPOJO.class);

        Assert.assertEquals(response.getUsername(), getUser.getUsername());
    }

    @Test(priority = 4)
    public void updateUserTest() {
        RestAssured.responseSpecification = ResponseCode.resSpecUnique(200);

        UpdateUserPOJO updateUser = new UpdateUserPOJO(userVictor.getUsername(), userVictor.getId(), "Tony228", userVictor.getLastName(), userVictor.getEmail(),
                userVictor.getPassword(), userVictor.getPhone(), userVictor.getUserStatus());

        UserResponseBodyPOJO response = given()
                .body(updateUser)
                .when()
                .put("/user/" + updateUser.getUsername())
                .then()
                .extract().as(UserResponseBodyPOJO.class);

        Assert.assertNotNull(response);
        Assert.assertNotSame(updateUser.getFirstName(), userVictor.getFirstName());
    }

    @Test(priority = 5)
    public void deleteUserTest() {
        RestAssured.responseSpecification = ResponseCode.resSpecUnique(200);

        LoginUserPOJO user = new LoginUserPOJO(userVictor.getUsername(), userVictor.getPassword());

        Response response = given()
                .when()
                .delete("/user/" + user.getUsername())
                .then()
                .extract().response();

        Assert.assertNotNull(response);
    }
}