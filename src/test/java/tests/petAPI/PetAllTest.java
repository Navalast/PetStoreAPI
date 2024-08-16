package tests.petAPI;

import confForTests.ResponseCode;
import confForTests.Setup;
import enumStatus.PetStatusEnum;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojo.petPojo.*;

import java.io.File;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static enumStatus.PetStatusEnum.*;
import static io.restassured.RestAssured.given;
import static java.lang.String.format;
import static org.testng.Assert.assertEquals;

public class PetAllTest extends Setup {

    private final PetPOJO dogVictor = PetPOJO.builder()
            .id(BigInteger.valueOf(1219872))
            .category(CategoryPOJO.builder()
                    .id(BigInteger.valueOf(2))
                    .name("BigDog")
                    .build())
            .name("Victor")
            .photoUrls(List.of("Link1", "Link2"))
            .tags(List.of(TagPOJO.builder()
                            .id(BigInteger.valueOf(3))
                            .name("firstTag")
                            .build(),
                    TagPOJO.builder()
                            .id(BigInteger.valueOf(4))
                            .name("secondTag")
                            .build()))
            .status(String.valueOf(PENDING).toLowerCase())
            .build();

    @Test
    @Description("get-запрос. Найти животное по статусу")
    public void getPetFindByStatusTest() {
        RestAssured.responseSpecification = ResponseCode.resSpecUnique(200);

        List<PetStatusEnum> listOfStatus = Stream.of(AVAILABLE, PENDING, SOLD)
                .collect(Collectors.toCollection(LinkedList::new));

        List<PetPOJO> response = given()
                .when()
                .get(format("/pet/findByStatus?status=%s", listOfStatus.get(2).toString().toLowerCase()))
                .then()
                .extract().body().jsonPath().getList(".", PetPOJO.class);

        response.forEach(pet -> assertEquals(pet.getStatus(), listOfStatus.get(2).toString().toLowerCase()));
    }

    @Test(priority = 1)
    @Description("post-запрос. Создать животное")
    public void postCreatePetTest() {
        RestAssured.responseSpecification = ResponseCode.resSpecUnique(200);

        PetPOJO response = given()
                .body(dogVictor)
                .when()
                .post("/pet")
                .then()
                .extract().as(PetPOJO.class);

        assertEquals(response.getId(), dogVictor.getId());
        assertEquals(response.getName(), dogVictor.getName());
        assertEquals(response.getStatus(), dogVictor.getStatus());
    }

    @Test(priority = 2)
    @Description("post-запрос. Загрузить картинку животному по id")
    public void postUploadImageTest() {
        RestAssured.responseSpecification = ResponseCode.resSpecUnique(200);

        File file = new File("src/test/resources/photo/images.png");
        UploadImagePOJO uploadImage = UploadImagePOJO.builder()
                .petId(dogVictor.getId())
                .additionalMetadata("Some data")
                .file(file)
                .build();

        assertEquals(uploadImage.getPetId(), dogVictor.getId());

        UpdateResponsePOJO response = given()
                .contentType("multipart/form-data")
                .multiPart("file", uploadImage.getFile())
                .formParam("additionalMetadata", uploadImage.getAdditionalMetadata())
                .when()
                .post(format("/pet/%s/uploadImage", uploadImage.getPetId()))
                .then()
                .extract().as(UpdateResponsePOJO.class);

        Assert.assertNotNull(response);
        Assert.assertTrue(response.getMessage().contains(uploadImage.getAdditionalMetadata()));
    }

    @Test(priority = 3)
    @Description("get-запрос. Найти животное по id")
    public void getFindPetByIdTest() {
        RestAssured.responseSpecification = ResponseCode.resSpecUnique(200);

        PetPOJO pet = given()
                .when()
                .get(format("/pet/%s", dogVictor.getId()))
                .then()
                .extract().body().as(PetPOJO.class);

        assertEquals(pet.getId(), dogVictor.getId());
        assertEquals(pet.getName(), dogVictor.getName());
        assertEquals(pet.getStatus(), dogVictor.getStatus());
    }

    @Test(priority = 4)
    @Description("put-запрос. Обновить данные о животном")
    public void putUpdatePetTest() {
        RestAssured.responseSpecification = ResponseCode.resSpecUnique(200);

        PetPOJO pet = given()
                .body(dogVictor)
                .when()
                .put("/pet")
                .then()
                .extract().body().as(PetPOJO.class);

        assertEquals(pet.getId(), dogVictor.getId());
        assertEquals(pet.getName(), dogVictor.getName());
        assertEquals(pet.getStatus(), dogVictor.getStatus());

    }

    @Test(priority = 5)
    @Description("post-запрос. Обновить данные о животном")
    public void postUpdatePetTest() {
        RestAssured.responseSpecification = ResponseCode.resSpecUnique(200);

        UpdatePetPOJO update = UpdatePetPOJO.builder()
                .id(dogVictor.getId())
                .name("Alex")
                .status("sleep")
                .build();

        UpdateResponsePOJO pet = given()
                .body(update)
                .contentType("application/x-www-form-urlencoded")
                .when()
                .post(format("/pet/%s", dogVictor.getId()))
                .then()
                .extract().body().as(UpdateResponsePOJO.class);

        assertEquals(pet.getMessage(), update.getId().toString());
    }

    @Test(priority = 6)
    @Description("delete-запрос. Удалить животное из списка")
    public void deleteTest() {
        RestAssured.responseSpecification = ResponseCode.resSpecUnique(200);

        UpdateResponsePOJO delete = given()
                .when()
                .delete(format("/pet/%s", dogVictor.getId()))
                .then()
                .extract().body().as(UpdateResponsePOJO.class);

        assertEquals(delete.getMessage(), dogVictor.getId().toString());
    }
}