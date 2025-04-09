package savorly.backend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import savorly.backend.dtos.Requests.CategoryCreateRequestDto;
import savorly.backend.dtos.Requests.CategoryUpdateRequestDto;
import savorly.backend.dtos.responses.CategoryResponseDto;
import savorly.backend.exceptions.ConflictException;
import savorly.backend.exceptions.NotFoundException;
import savorly.backend.models.CategoryModel;
import savorly.backend.repositories.CategoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {
     private final CategoryRepository categoryRepository;

     public CategoryResponseDto create(CategoryCreateRequestDto categoryCreateRequestDto){
         if(this.categoryRepository.findByName(categoryCreateRequestDto.getName()).isPresent()){
             throw new ConflictException("There is already a category with this name");
         }

        // fac un obiect RecipeTypeModel
         CategoryModel categoryModel = new CategoryModel();
        // il umplu cu datele din CreateDto
        categoryModel.setName(categoryCreateRequestDto.getName());
        // il trimit la Repository si primesc raspuns un alt Model
        CategoryModel createdCategoryModel = this.categoryRepository.save(categoryModel);
        // fac un obiect ResponseDto
        CategoryResponseDto categoryResponseDto = new CategoryResponseDto();
        // umplu cu datele din obiectul Model pe care l-am primit raspuns de la Repository
        categoryResponseDto.setId(createdCategoryModel.getId());
        categoryResponseDto.setName(createdCategoryModel.getName());
        // il returnez
        return categoryResponseDto;
     }

     public List<CategoryResponseDto> getAll(){
         List<CategoryModel> categoryModels = this.categoryRepository.findAll();
         List<CategoryResponseDto> categoryResponseDtos = new ArrayList<>();

         for (CategoryModel categoryModel : categoryModels) {
            CategoryResponseDto categoryResponseDto = new CategoryResponseDto();

            categoryResponseDto.setId(categoryModel.getId());
            categoryResponseDto.setName(categoryModel.getName());
            categoryResponseDtos.add(categoryResponseDto);

         }

         return categoryResponseDtos;
     }

    public CategoryResponseDto getDtoById(UUID id) {
        Optional<CategoryModel> optionalCategoryModel = this.categoryRepository.findById(id);

        if (optionalCategoryModel.isEmpty()) {
            return null;
        }

        CategoryModel recipeTypeModel = optionalCategoryModel.get();

        CategoryResponseDto recipeTypeResponseDto = new CategoryResponseDto();

        recipeTypeResponseDto.setId(recipeTypeModel.getId());
        recipeTypeResponseDto.setName(recipeTypeModel.getName());
        return recipeTypeResponseDto;

    }

    public CategoryModel getModelById(UUID id) {
        return this.categoryRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("There is no category with this id"));
    }

    public void deleteAll() {
        this.categoryRepository.deleteAll();
    }

    public void deleteById(UUID id) {
        this.categoryRepository.deleteById(id);
    }

    public CategoryResponseDto updateById(UUID id, CategoryUpdateRequestDto categoryUpdateRequestDto) {
        CategoryModel categoryModel = this.categoryRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Nu exista nicio categorie cu ID-ul " + id));

        categoryModel.setName(categoryUpdateRequestDto.getName());

       CategoryModel updatedCategoryModel = categoryRepository.save(categoryModel);

        CategoryResponseDto categoryResponseDto = new CategoryResponseDto();
        categoryResponseDto.setId(updatedCategoryModel.getId());
        categoryResponseDto.setName(updatedCategoryModel.getName());

        return categoryResponseDto;
    }

}
