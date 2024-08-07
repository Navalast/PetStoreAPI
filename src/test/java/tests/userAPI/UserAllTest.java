package tests.userAPI;

import confForTests.ResponseCode;
import confForTests.Setup;
import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojo.userPojo.*;

import static io.restassured.RestAssured.given;
import static java.lang.String.format;

public class UserAllTest extends Setup {

    private final UserPOJO userTony = UserPOJO.builder()
            .id(121)
            .username("Krasavec")
            .firstName("Tony")
            .lastName("Krasavkovich")
            .email("mail@mail.ru")
            .password("2225")
            .phone("98316516")
            .userStatus(21)
            .build();

    @Test(priority = 1)
    public void createUserTest() {
        RestAssured.responseSpecification = ResponseCode.resSpecUnique(200);

        UserResponseBodyPOJO response = given()
                .body(userTony)
                .when()
                .post("/user")
                .then()
                .extract().as(UserResponseBodyPOJO.class);

        Assert.assertNotNull(response);

        Assert.assertTrue(response.getMessage().contains(userTony.getId().toString()));
    }

    @Test(priority = 2)
    public void getUserLoginTest() {
        RestAssured.responseSpecification = ResponseCode.resSpecUnique(200);

        LoginUserPOJO user = LoginUserPOJO.builder()
                .username(userTony.getUsername())
                .password(userTony.getPassword())
                .build();

        Assert.assertEquals(user.getUsername(), userTony.getUsername());
        Assert.assertEquals(user.getPassword(), userTony.getPassword());

        UserResponseBodyPOJO response = given()
                .body(user)
                .when()
                .get("/user/login")
                .then()
                .extract().as(UserResponseBodyPOJO.class);

        Assert.assertNotNull(response);

    }

    @Test(priority = 3)
    public void getUserTest() {
        RestAssured.responseSpecification = ResponseCode.resSpecUnique(200);

        GetUserPOJO getUser = GetUserPOJO.builder()
                .username(userTony.getUsername())
                .build();

        Assert.assertEquals(getUser.getUsername(), userTony.getUsername());

        UserPOJO response = given()
                .body(getUser)
                .when()
                .get(format("/user/%s", getUser.getUsername()))
                .then()
                .extract().as(UserPOJO.class);

        Assert.assertEquals(response.getUsername(), getUser.getUsername());
        Assert.assertNotNull(response);
    }

    @Test(priority = 4)
    public void updateUserTest() {
        RestAssured.responseSpecification = ResponseCode.resSpecUnique(200);

        UpdateUserPOJO updateUser = UpdateUserPOJO.builder()
                .username(userTony.getUsername())
                .id(userTony.getId())
                .firstName("Tony228")
                .lastName(userTony.getLastName())
                .email(userTony.getEmail())
                .password(userTony.getPassword())
                .phone(userTony.getPhone())
                .userStatus(userTony.getUserStatus())
                .build();

        UserResponseBodyPOJO response = given()
                .body(updateUser)
                .when()
                .put(format("/user/%s", updateUser.getUsername()))
                .then()
                .extract().as(UserResponseBodyPOJO.class);

        Assert.assertNotNull(response);
        Assert.assertNotSame(updateUser.getFirstName(), userTony.getFirstName());
    }

    @Test(priority = 5)
    public void deleteUserTest() {
        RestAssured.responseSpecification = ResponseCode.resSpecUnique(200);

        LoginUserPOJO user = LoginUserPOJO.builder()
                .username(userTony.getUsername())
                .password(userTony.getPassword())
                .build();

        Assert.assertEquals(user.getUsername(), userTony.getUsername());
        Assert.assertEquals(user.getPassword(), userTony.getPassword());

        UserResponseBodyPOJO response = given()
                .when()
                .delete(format("/user/%s", user.getUsername()))
                .then()
                .extract().as(UserResponseBodyPOJO.class);

        Assert.assertNotNull(response);
        Assert.assertTrue(response.getMessage().contains(user.getUsername()));
    }
}