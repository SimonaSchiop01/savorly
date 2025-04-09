package savorly.backend.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import savorly.backend.dtos.Requests.RecipeCreateRequestDto;
import savorly.backend.dtos.responses.RecipeResponseDto;
import savorly.backend.models.RecipeModel;

@Component
@Mapper(
    componentModel = "spring",
    uses={
        CategoryMapper.class,
        RecipeIngredientMapper.class
    }
)
public interface RecipeMapper {
    @Mapping(target="id", ignore = true)
    @Mapping(target="category", ignore = true)
    @Mapping(target="recipeIngredients", ignore = true)
    RecipeModel toModel(RecipeCreateRequestDto dto);

    RecipeResponseDto toDto(RecipeModel model);

}