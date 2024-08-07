package tests.storeAPI;

import com.fasterxml.jackson.annotation.JsonFormat;
import confForTests.ResponseCode;
import confForTests.Setup;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojo.storePojo.DeleteResponsePOJO;
import pojo.storePojo.OrderPOJO;

import java.util.Date;

import static io.restassured.RestAssured.given;
import static java.lang.String.format;

public class StoreAllTest extends Setup {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone = "UTC")
    private Date date;

    private final OrderPOJO firstOrder = OrderPOJO.builder()
            .id(121)
            .petId(4)
            .quantity(4)
            .shipDate(date)
            .status("placed")
            .complete(true)
            .build();

    @Test(priority = 1)
    public void postStoreTest() {
        RestAssured.responseSpecification = ResponseCode.resSpecUnique(200);

        OrderPOJO response = given()
                .body(firstOrder)
                .when()
                .post("/store/order/")
                .then()
                .extract().as(OrderPOJO.class);

        Assert.assertNotNull(response);
        Assert.assertEquals(firstOrder.getId(), response.getId());
        Assert.assertEquals(firstOrder.getPetId(), response.getPetId());
    }

    @Test(priority = 2)
    public void getOrderOnIdTest() {
        RestAssured.responseSpecification = ResponseCode.resSpecUnique(200);

        OrderPOJO response = given()
                .when()
                .get(format("/store/order/%s", firstOrder.getId()))
                .then()
                .extract().body().as(OrderPOJO.class);

        Assert.assertNotNull(response);
        Assert.assertEquals(firstOrder.getId(), response.getId());
        Assert.assertEquals(firstOrder.getPetId(), response.getPetId());
    }

    @Test(priority = 3)
    public void getOrderInventoryTest() {
        RestAssured.responseSpecification = ResponseCode.resSpecUnique(200);

        Response response = given()
                .when()
                .get("/store/inventory")
                .then()
                .extract().response();

        Assert.assertNotNull(response);
    }

    @Test(priority = 4)
    public void deleteTest() {
        RestAssured.responseSpecification = ResponseCode.resSpecUnique(200);

        DeleteResponsePOJO response = given()
                .when()
                .delete(format("/store/order/%s", firstOrder.getId()))
                .then()
                .extract().body().as(DeleteResponsePOJO.class);

        Assert.assertNotNull(response);
        Assert.assertEquals(response.getMessage(), firstOrder.getId().toString());
    }
}