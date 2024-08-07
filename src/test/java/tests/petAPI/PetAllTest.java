package tests.petAPI;

import confForTests.ResponseCode;
import confForTests.Setup;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojo.petPojo.*;

import java.io.File;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class PetAllTest extends Setup {

    private final PetPOJO dogVictor = PetPOJO.builder()
            .id(10)
            .category(CategoryPOJO.builder()
                    .id(2)
                    .name("BigDog")
                    .build())
            .name("Victor")
            .photoUrls(List.of("Link1", "Link2"))
            .tags(List.of(TagPOJO.builder()
                    .id(3)
                    .name("firstTag")
                    .build(),
                    TagPOJO.builder()
                            .id(4)
                            .name("secondTag")
                            .build()))
            .status("sleep")
            .build();

    @Test(priority = 1)
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
    public void postUploadImageTest() {
        RestAssured.responseSpecification = ResponseCode.resSpecUnique(200);

        File file = new File("src/test/resources/photo/images.png");
        UploadImagePOJO uploadImage = UploadImagePOJO.builder()
                .petId(dogVictor.getId())
                .additionalMetadata("Some data")
                .file(file)
                .build();

        UpdateResponsePOJO response = given()
                .contentType("multipart/form-data")
                .multiPart("file", uploadImage.getFile())
                .formParam("additionalMetadata", uploadImage.getAdditionalMetadata())
                .when()
                .post("/pet/" + uploadImage.getPetId() + "/uploadImage")
                .then()
                .extract().as(UpdateResponsePOJO.class);

        assertEquals(uploadImage.getPetId(), dogVictor.getId());
        Assert.assertNotNull(response);
        Assert.assertTrue(response.getMessage().contains(uploadImage.getAdditionalMetadata()));

    }

    @Test(priority = 3)
    public void getFindPetByIdTest() {
        RestAssured.responseSpecification = ResponseCode.resSpecUnique(200);

        PetPOJO pet = given()
                .when()
                .get("/pet/" + dogVictor.getId())
                .then()
                .extract().body().as(PetPOJO.class);

        assertEquals(pet.getId().intValue(), 10);
        assertEquals(pet.getName(), "Victor");
        assertEquals(pet.getStatus(), "sleep");
    }

    @Test(priority = 4)
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
    public void postUpdatePetTest() {
        RestAssured.responseSpecification = ResponseCode.resSpecUnique(200);
        UpdatePetPOJO update = UpdatePetPOJO.builder()
                .id(10)
                .name("Alex")
                .status("sleep")
                .build();

        UpdateResponsePOJO pet = given()
                .body(update)
                .contentType("application/x-www-form-urlencoded")
                .when()
                .post("/pet/" + dogVictor.getId())
                .then()
                .extract().body().as(UpdateResponsePOJO.class);

        assertEquals(pet.getMessage(), update.getId().toString());
    }

    @Test(priority = 6)
    public void deleteTest() {
        RestAssured.responseSpecification = ResponseCode.resSpecUnique(200);

        UpdateResponsePOJO delete = given()
                .when()
                .delete("/pet/" + dogVictor.getId())
                .then()
                .extract().body().as(UpdateResponsePOJO.class);

        assertEquals(delete.getMessage(), dogVictor.getId().toString());
    }
}