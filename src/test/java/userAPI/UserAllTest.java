package userAPI;

import confForTests.ResponseCode;
import confForTests.Setup;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import userAPI.pojo.GetUser;
import userAPI.pojo.LoginUser;
import userAPI.pojo.UpdateUser;
import userAPI.pojo.User;

import static io.restassured.RestAssured.given;

public class UserAllTest extends Setup {

    @Test
    public void createUserTest() {
        RestAssured.responseSpecification = ResponseCode.resSpecUnique(200);

        User user = new User(10, "Krasavec", "Tony", "Krasavkovich", "mail@mail.ru",
                "2225", "98316516", 1);

        Response response = given()
                .body(user)
                .when()
                .post("/user")
                .then()
                .extract().response();

        Assert.assertNotNull(response);
    }

    @Test(dependsOnMethods = "createUserTest")
    public void getUserLoginTest() {
        RestAssured.responseSpecification = ResponseCode.resSpecUnique(200);

        LoginUser user = new LoginUser("Krasavec", "2225");
        Response response = given()
                .body(user)
                .when()
                .get("/user/login")
                .then()
                .extract().response();

        Assert.assertNotNull(response);
    }

    @Test(dependsOnMethods = "getUserLoginTest")
    public void getUserTest() {
        RestAssured.responseSpecification = ResponseCode.resSpecUnique(200);

        GetUser getUser = new GetUser("Krasavec");
        Response response = given()
                .body(getUser)
                .when()
                .get("/user/" + getUser.getUsername())
                .then()
                .extract().response();
    }

    @Test(dependsOnMethods = "getUserTest")
    public void updateUserTest() {
        RestAssured.responseSpecification = ResponseCode.resSpecUnique(200);

        LoginUser user = new LoginUser("Krasavec", "2225");
        GetUser getUser = new GetUser("Krasavec");

        Response response = given()
                .body(user)
                .when()
                .get("/user/login")
                .then()
                .extract().response();

        UpdateUser updateUser = new UpdateUser(user.getUsername(), 10, "Tony228", "Krasavkovich", "mail@mail.ru",
                "2225", "98316516", 1);

        Response response2 = given()
                .body(updateUser)
                .when()
                .put("/user/" + getUser.getUsername())
                .then()
                .extract().response();
    }

    @Test(dependsOnMethods = "updateUserTest")
    public void deleteUserTest() {
        RestAssured.responseSpecification = ResponseCode.resSpecUnique(200);

        LoginUser user = new LoginUser("Krasavec", "2225");

        Response auth = given()
                .body(user)
                .when()
                .get("/user/login")
                .then()
                .extract().response();

        Response response = given()
                .when()
                .delete("/user/" + user.getUsername())
                .then()
                .extract().response();
    }
}
