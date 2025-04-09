package savorly.backend.dtos.responses;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Data
public class RecipeResponseDto {
    private UUID id;
    private String name;
    private String description;
    private String photoUri;
    private String preparationTime;
    private CategoryResponseDto category;
    private List<RecipeIngredientResponseDto> recipeIngredients;
}