package petAPI;

import petAPI.pojo.Category;
import petAPI.pojo.Pet;
import petAPI.pojo.Tag;

import java.util.List;

public class CreatePet {
    public static Pet createPetObject(int categoryId, String categoryName, int tagId, String tagName, int petId, String petName, List<String> urls, String status) {
        Category categoryOfPet = new Category(categoryId, categoryName);
        Tag tagOfPet = new Tag(tagId, tagName);
        return new Pet(categoryOfPet, petId, petName, urls, status, List.of(tagOfPet));
    }
}
