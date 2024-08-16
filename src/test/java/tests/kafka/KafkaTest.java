package tests.kafka;

import com.fasterxml.jackson.annotation.JsonFormat;
import confForTests.ResponseCode;
import confForTests.Setup;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import kafka.KafkaConsumerHelper;
import kafka.KafkaProducerHelper;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pojo.storePojo.OrderPOJO;

import java.math.BigInteger;
import java.util.Date;

import static enumStatus.OrderStatusEnum.PLACED;
import static io.restassured.RestAssured.given;

public class KafkaTest extends Setup {

    private KafkaProducerHelper kafkaProducer;
    private KafkaConsumerHelper kafkaConsumer;

    @BeforeClass
    public void setUp() {
        kafkaProducer = new KafkaProducerHelper("localhost:9092");
        kafkaConsumer = new KafkaConsumerHelper("localhost:9092", "unique-test-group", "test-topic");
    }

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
    @Description("post-запрос. Создания заказа, взаимодействие с кафка с Kafka")
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

        String testResult = String.format("Test passed: Order ID %d, Pet ID %d", response.getId(), response.getPetId());
        kafkaProducer.sendMessage("test-topic", "unique-test-group", testResult);

        String receivedMessage = kafkaConsumer.consumeMessage();
        System.out.println(receivedMessage);
        Assert.assertNotNull(receivedMessage, "Kafka message should not be null");
        Assert.assertTrue(receivedMessage.contains("Test passed"), "Kafka message should contain 'Test passed'");

    }

    @AfterClass
    public void tearDown() {
        kafkaProducer.close();
        kafkaConsumer.close();
    }
}