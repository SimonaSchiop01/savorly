package savorly.backend.mappers;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import savorly.backend.dtos.responses.CategoryResponseDto;
import savorly.backend.models.CategoryModel;

@Component
@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryResponseDto toDto(CategoryModel model);
}
