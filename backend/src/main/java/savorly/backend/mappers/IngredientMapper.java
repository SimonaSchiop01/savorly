package savorly.backend.mappers;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import savorly.backend.models.IngredientModel;
import savorly.backend.dtos.responses.IngredientResponseDto;

@Component
@Mapper(componentModel = "spring")
public interface IngredientMapper {
    IngredientResponseDto toDto(IngredientModel model);
}