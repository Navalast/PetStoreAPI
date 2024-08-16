package tests.userAPI;

import confForTests.ResponseCode;
import confForTests.Setup;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojo.userPojo.UserResponseBodyPOJO;

import static io.restassured.RestAssured.given;

public class UsersAllNegativeTest extends Setup {

    private final String type = "unknown";
    private final String body = "";

    @Test(priority = 1)
    @Description("Неудачная попытка создать юзера с целым числом вместо тела, получение ошибки 500")
    public void negativeCreateUserTest() {
        RestAssured.responseSpecification = ResponseCode.resSpecUnique(500);

        UserResponseBodyPOJO response = given()
                .body(2)
                .when()
                .post("/user")
                .then()
                .extract().as(UserResponseBodyPOJO.class);

        String message = "something bad happened";

        Assert.assertNotNull(response);
        Assert.assertTrue(response.getType().contains(type));
        Assert.assertTrue(response.getMessage().contains(message));
    }

    @Test(priority = 2)
    @Description("Неудачная попытка создать юзера с пустым телом, получение ошибки 405")
    public void negativeCreateUser2Test() {
        RestAssured.responseSpecification = ResponseCode.resSpecUnique(405);

        UserResponseBodyPOJO response = given()
                .body(body)
                .when()
                .post("/user")
                .then()
                .extract().as(UserResponseBodyPOJO.class);

        String message = "no data";

        Assert.assertNotNull(response);
        Assert.assertTrue(response.getType().contains(type));
        Assert.assertTrue(response.getMessage().contains(message));

    }

    @Test(priority = 3)
    @Description("Неудачная попытка создать юзера, использование неправильный endpoint получение ошибки 405")
    public void negativeCreateUser3Test() {
        RestAssured.responseSpecification = ResponseCode.resSpecUnique(405);

        UserResponseBodyPOJO response = given()
                .body(body)
                .when()
                .post("/user/post")
                .then()
                .extract().as(UserResponseBodyPOJO.class);

        Assert.assertNotNull(response);
        Assert.assertTrue(response.getType().contains(type));

    }
}