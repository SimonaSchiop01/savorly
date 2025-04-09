package savorly.backend.dtos.Requests;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class RecipeIngredientUpdateRequestDto {
    private UUID ingredientId;
    private String quantity;
}
