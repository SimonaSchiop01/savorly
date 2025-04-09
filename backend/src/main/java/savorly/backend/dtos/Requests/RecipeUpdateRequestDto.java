package savorly.backend.dtos.Requests;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
public class RecipeUpdateRequestDto {
    private String name;
    private String description;
    private String photoUri;
    private String preparationTime;
    private UUID categoryId;
    private List<RecipeIngredientCreateRequestDto> recipeIngredients;
}