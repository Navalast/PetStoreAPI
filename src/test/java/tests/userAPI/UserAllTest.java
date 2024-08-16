package tests.userAPI;

import confForTests.ResponseCode;
import confForTests.Setup;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojo.userPojo.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static java.lang.String.format;

public class UserAllTest extends Setup {

    private final UserPOJO userTony = UserPOJO.builder()
            .id(BigInteger.valueOf(1219872))
            .username("Krasavec")
            .firstName("Tony")
            .lastName("Krasavkovich")
            .email("mail@mail.ru")
            .password("2225")
            .phone("98316516")
            .userStatus(21)
            .build();

    private final UserPOJO userVictor = UserPOJO.builder()
            .id(BigInteger.valueOf(3480872))
            .username("Victor5000")
            .firstName("Victor")
            .lastName("Victorovich")
            .email("mailemail@mail.ru")
            .password("1337")
            .phone("613216")
            .userStatus(4)
            .build();

    private List<UserPOJO> userPOJOList;

    @Test()
    @Description("post-запрос. Создать список пользователей используя LinkedList")
    public void createUserWithListTest() {
        RestAssured.responseSpecification = ResponseCode.resSpecUnique(200);

        userPOJOList = new LinkedList<>();
        userPOJOList.add(userTony);
        userPOJOList.add(userVictor);

        Assert.assertEquals(userTony, userPOJOList.get(0));
        Assert.assertEquals(userVictor, userPOJOList.get(1));

        UserResponseBodyPOJO response = given()
                .body(userPOJOList)
                .when()
                .post("/user/createWithArray")
                .then()
                .extract().as(UserResponseBodyPOJO.class);

        String message = "ok";
        String type = "unknown";

        Assert.assertNotNull(response);
        Assert.assertEquals(message, response.getMessage());
        Assert.assertEquals(type, response.getType());
    }

    @Test()
    @Description("post-запрос. Создать список пользователей используя ArrayList")
    public void createUserWithArrayTest() {
        RestAssured.responseSpecification = ResponseCode.resSpecUnique(200);

        userPOJOList = new ArrayList<>();
        userPOJOList.add(userTony);
        userPOJOList.add(userVictor);

        Assert.assertEquals(userTony, userPOJOList.get(0));
        Assert.assertEquals(userVictor, userPOJOList.get(1));

        UserResponseBodyPOJO response = given()
                .body(userPOJOList)
                .when()
                .post("/user/createWithArray")
                .then()
                .extract().as(UserResponseBodyPOJO.class);

        String message = "ok";
        String type = "unknown";

        Assert.assertNotNull(response);
        Assert.assertEquals(message, response.getMessage());
        Assert.assertEquals(type, response.getType());
    }

    @Test(priority = 1)
    @Description("post-запрос. Создать пользователя")
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
    @Description("get-запрос. Авторизировать пользователя")
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
    @Description("get-запрос. Найти пользователя по имени")
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
    @Description("put-запрос. Обновить данные о пользователе")
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
                .userStatus(BigInteger.valueOf(userTony.getUserStatus()))
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
    @Description("delete-запрос. Удалить пользователя")
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