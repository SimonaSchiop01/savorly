package savorly.backend.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import savorly.backend.models.RecipeIngredientModel;
import savorly.backend.dtos.Requests.RecipeIngredientCreateRequestDto;
import savorly.backend.dtos.responses.RecipeIngredientResponseDto;

@Component
@Mapper(
    componentModel = "spring",
    uses={IngredientMapper.class}
)
public interface RecipeIngredientMapper {
//    dintr-un obiect mai simplu intr-unul mai complex
    @Mapping(target="id", ignore = true)
    @Mapping(target="recipe", ignore = true)
    @Mapping(target="ingredient", ignore = true)
    RecipeIngredientModel toModel(RecipeIngredientCreateRequestDto requestDto);

    RecipeIngredientResponseDto toDto(RecipeIngredientModel model);
}
