package tests.userAPI;

import confForTests.ResponseCode;
import confForTests.Setup;
import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojo.userPojo.UserResponseBodyPOJO;

import static io.restassured.RestAssured.given;

public class UsersAllNegativeTest extends Setup {

    private final String type = "unknown";
    private final String body = "";

    @Test(priority = 1)
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