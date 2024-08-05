package petAPI;

import confForTests.ResponseCode;
import confForTests.Setup;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import petAPI.pojo.*;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class PetAllTest extends Setup {

    private final Pet dog = Pet.builder()
            .id(1)
            .category(Category.builder()
                    .id(2)
                    .name("BigDog")
                    .build())
            .name("GoodBoy")
            .tags(List.of(Tag.builder()
                            .id(3)
                            .name("link1")
                    .build(),
                    Tag.builder()
                            .id(4)
                            .name("link2")
                            .build()))
            .status("sleep")
            .build();

    @Test(priority = 1)
    public void postCreatePet() {
        RestAssured.responseSpecification = ResponseCode.resSpecUnique(200);

        Response response = given()
                .body(dog)
                .when()
                .post("/pet")
                .then()
                .extract().response();

        Pet createdPet = response.getBody().as(Pet.class);

        assertEquals(createdPet.getId(), dog.getId());
        assertEquals(createdPet.getName(), dog.getName());
        assertEquals(createdPet.getStatus(), dog.getStatus());
    }

    @Test(priority = 2)
    public void getFindPetById() {
        RestAssured.responseSpecification = ResponseCode.resSpecUnique(200);

        Pet pet = given()
                .when()
                .get("/pet/10")
                .then()
                .extract().body().as(Pet.class);

        assertEquals(pet.getId().intValue(), 10);
        assertEquals(pet.getName(), "Victor");
        assertEquals(pet.getStatus(), "sleep");
    }

    @Test(priority = 3)
    public void putUpdatePet() {
        RestAssured.responseSpecification = ResponseCode.resSpecUnique(200);

        Pet pet = given()
                .body(dog)
                .when()
                .put("/pet")
                .then()
                .extract().body().as(Pet.class);

        assertEquals(pet.getId(), dog.getId());
        assertEquals(pet.getName(), dog.getName());
        assertEquals(pet.getStatus(), dog.getStatus());

    }

    @Test(priority = 4)
    public void postUpdatePet() {
        RestAssured.responseSpecification = ResponseCode.resSpecUnique(200);
        UpdatePet update = UpdatePet.builder()
                .id(10)
                .name("Alex")
                .status("sleep")
                .build();

        UpdateResponse pet = given()
                .body(update)
                .contentType("application/x-www-form-urlencoded")
                .when()
                .post("/pet/10")
                .then()
                .extract().body().as(UpdateResponse.class);

        assertEquals(pet.getMessage(), update.getId().toString());
    }

    @Test(priority = 5)
    public void deleteTest() {
        RestAssured.responseSpecification = ResponseCode.resSpecUnique(200);

        UpdateResponse delete = given()
                .when()
                .delete("/pet/" + dog.getId())
                .then()
                .extract().body().as(UpdateResponse.class);

        assertEquals(delete.getMessage(), dog.getId().toString());
    }
}