package savorly.backend.dtos.Requests;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class RecipeCreateRequestDto {
    private String name;
    private String description;
    private String photoUri;
    private String preparationTime;
    private UUID categoryId;
    private List<RecipeIngredientCreateRequestDto> recipeIngredients;
}