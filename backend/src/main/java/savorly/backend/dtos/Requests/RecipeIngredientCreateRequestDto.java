package savorly.backend.dtos.Requests;

import lombok.Data;

import java.util.UUID;

@Data
public class RecipeIngredientCreateRequestDto {
    private UUID ingredientId;
    private String quantity;
}