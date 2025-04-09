package savorly.backend.dtos.responses;

import lombok.Data;
import java.util.UUID;

@Data
public class RecipeIngredientResponseDto {
    private UUID id;
    private IngredientResponseDto ingredient;
    private String quantity;
}
