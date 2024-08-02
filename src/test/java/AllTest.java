import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pojo.LoginUser;
import pojo.GetUser;
import pojo.UpdateUser;
import pojo.User;
import responseCode.ResponseCode;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class AllTest {

    @BeforeClass
    public void setup() {
        try {
            System.getProperties().load(ClassLoader.getSystemResourceAsStream("config.properties"));
        } catch (IOException e) {
            throw new RuntimeException("Ошибка загрузки файла config.properties", e);
        }

        String baseUri = System.getProperty("base.uri");

        if (baseUri == null || baseUri.isEmpty()) {
            throw new RuntimeException("В файле \"config.properties\" отсутствует значение \"base.uri\"");
        }

        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setBaseUri(baseUri) // Задаем базовый URI для всех запросов
                .setAccept(ContentType.JSON) // Задаем заголовок accept
                .setContentType(ContentType.JSON) // Задаем заголовок content-type
                .log(LogDetail.ALL) // Логгирование всех деталей запросов и ответов
                .build();
        RestAssured.filters(new ResponseLoggingFilter());
    }

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
