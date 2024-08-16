package tests.storeAPI;

import com.fasterxml.jackson.annotation.JsonFormat;
import confForTests.ResponseCode;
import confForTests.Setup;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojo.storePojo.DeleteResponsePOJO;
import pojo.storePojo.OrderPOJO;

import java.math.BigInteger;
import java.util.Date;

import static enumStatus.OrderStatusEnum.PLACED;
import static io.restassured.RestAssured.given;
import static java.lang.String.format;

public class StoreAllTest extends Setup {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone = "UTC")
    private Date date;

    private final OrderPOJO firstOrder = OrderPOJO.builder()
            .id(BigInteger.valueOf(1219872))
            .petId(BigInteger.valueOf(1219872))
            .quantity(BigInteger.valueOf(4))
            .shipDate(date)
            .status(PLACED.toString().toLowerCase())
            .complete(true)
            .build();

    @Test(priority = 1)
    @Description("post-запрос. Создать заказ")
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
    @Description("get-запрос. Получить инвентарь заказов")
    public void getOrderInventoryTest() {
        RestAssured.responseSpecification = ResponseCode.resSpecUnique(200);

        Response response = given()
                .when()
                .get("/store/inventory")
                .then()
                .extract().response();

        Assert.assertNotNull(response);
    }

    @Test(priority = 3)
    @Description("get-запрос. Найти заказ по id")
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

    @Test(priority = 4)
    @Description("delete-запрос. Удалить заказ по id")
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