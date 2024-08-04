package petAPI;

import confForTests.ResponseCode;
import confForTests.Setup;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import petAPI.pojo.Pet;
import petAPI.pojo.UpdatePet;
import petAPI.pojo.UpdateResponse;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class PetAllTest extends Setup {

    private Pet dogVictor = CreatePet.createPetObject(1, "BigDog", 2, "GoodBoy", 10, "Victor",
            List.of("link1", "link2"), "sleep");

    @Test
    public void postCreatePet() {
        RestAssured.responseSpecification = ResponseCode.resSpecUnique(200);

        Response response = given()
                .body(dogVictor)
                .when()
                .post("/pet")
                .then()
                .extract().response();

        Pet createdPet = response.getBody().as(Pet.class);

        assertEquals(createdPet.getId(), dogVictor.getId());
        assertEquals(createdPet.getName(), dogVictor.getName());
        assertEquals(createdPet.getStatus(), dogVictor.getStatus());
    }

    @Test(dependsOnMethods = "postCreatePet")
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

    @Test(dependsOnMethods = "getFindPetById")
    public void putUpdatePet() {
        RestAssured.responseSpecification = ResponseCode.resSpecUnique(200);

        Pet pet = given()
                .body(dogVictor)
                .when()
                .put("/pet")
                .then()
                .extract().body().as(Pet.class);

        assertEquals(pet.getId(), dogVictor.getId());
        assertEquals(pet.getName(), dogVictor.getName());
        assertEquals(pet.getStatus(), dogVictor.getStatus());

    }

    @Test(dependsOnMethods = "putUpdatePet")
    public void postUpdatePet() {
        RestAssured.responseSpecification = ResponseCode.resSpecUnique(200);
        UpdatePet update = new UpdatePet(10, "Alex", "sleep");

        UpdateResponse pet = given()
                .body(update)
                .contentType("application/x-www-form-urlencoded")
                .when()
                .post("/pet/10")
                .then()
                .extract().body().as(UpdateResponse.class);

        assertEquals(pet.getMessage(), update.getId().toString());
    }

    @Test(dependsOnMethods = "postUpdatePet")
    public void deleteTest() {
        RestAssured.responseSpecification = ResponseCode.resSpecUnique(200);

        UpdateResponse delete = given()
                .when()
                .delete("/pet/" + dogVictor.getId())
                .then()
                .extract().body().as(UpdateResponse.class);

        assertEquals(delete.getMessage(), dogVictor.getId().toString());
    }
}