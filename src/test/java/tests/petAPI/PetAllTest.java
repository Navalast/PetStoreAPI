package tests.petAPI;

import confForTests.ResponseCode;
import confForTests.Setup;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import pojo.petPojo.*;

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
    public void postCreatePet() {
        RestAssured.responseSpecification = ResponseCode.resSpecUnique(200);

        Response response = given()
                .body(dogVictor)
                .when()
                .post("/pet")
                .then()
                .extract().response();

        PetPOJO createdPet = response.getBody().as(PetPOJO.class);

        assertEquals(createdPet.getId(), dogVictor.getId());
        assertEquals(createdPet.getName(), dogVictor.getName());
        assertEquals(createdPet.getStatus(), dogVictor.getStatus());
    }

    @Test(priority = 2)
    public void getFindPetById() {
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

    @Test(priority = 3)
    public void putUpdatePet() {
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

    @Test(priority = 4)
    public void postUpdatePet() {
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

    @Test(priority = 5)
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