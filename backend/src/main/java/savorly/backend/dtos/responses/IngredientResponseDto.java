package savorly.backend.dtos.responses;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class IngredientResponseDto {
    private UUID id;
    private String name;
}
