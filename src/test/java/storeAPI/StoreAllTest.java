package storeAPI;

import com.fasterxml.jackson.annotation.JsonFormat;
import confForTests.ResponseCode;
import confForTests.Setup;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import storeAPI.pojo.DeleteResponse;
import storeAPI.pojo.Order;

import java.util.Date;

import static io.restassured.RestAssured.given;

public class StoreAllTest extends Setup {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone = "UTC")
    private Date date;

    private final Order firstOrder = Order.builder()
            .id(10)
            .petId(4)
            .quantity(4)
            .shipDate(date)
            .status("placed")
            .complete(true)
            .build();

    @Test(priority = 1)
    public void postStoreTest() {
        RestAssured.responseSpecification = ResponseCode.resSpecUnique(200);

        Order response = given()
                .body(firstOrder)
                .when()
                .post("/store/order/")
                .then()
                .extract().as(Order.class);

        Assert.assertNotNull(response);
        Assert.assertEquals(firstOrder.getId(), response.getId());
    }

    @Test(priority = 2)
    public void getOrderOnId() {
        RestAssured.responseSpecification = ResponseCode.resSpecUnique(200);

        Order response = given()
                .when()
                .get("/store/order/" + firstOrder.getId())
                .then()
                .extract().body().as(Order.class);

        Assert.assertNotNull(response);
        Assert.assertEquals(firstOrder.getId(), response.getId());
        Assert.assertEquals(firstOrder.getPetId(), response.getPetId());
    }

    @Test(priority = 3)
    public void getOrderInventory() {
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

        DeleteResponse response = given()
                .when()
                .delete("/store/order/" + firstOrder.getId())
                .then()
                .extract().body().as(DeleteResponse.class);

        Assert.assertNotNull(response);
        Assert.assertEquals(response.getMessage(), "10");
    }
}