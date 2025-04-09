package savorly.backend.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import savorly.backend.dtos.Requests.RecipeUpdateRequestDto;
import savorly.backend.exceptions.BadRequestException;
import savorly.backend.exceptions.NotFoundException;
import savorly.backend.mappers.RecipeIngredientMapper;
import savorly.backend.dtos.Requests.RecipeCreateRequestDto;
import savorly.backend.dtos.Requests.RecipeIngredientCreateRequestDto;
import savorly.backend.dtos.responses.RecipeResponseDto;
import savorly.backend.exceptions.ConflictException;
import savorly.backend.mappers.RecipeMapper;
import savorly.backend.models.CategoryModel;
import savorly.backend.models.IngredientModel;
import savorly.backend.models.RecipeIngredientModel;
import savorly.backend.models.RecipeModel;
import savorly.backend.repositories.RecipeRepository;

import java.io.IOException;
import java.util.*;

//Această adnotare (din Lombok) creează automat un logger pentru clasa respectivă, facilitând jurnalizarea (logging).
@Slf4j
@Service
@RequiredArgsConstructor
public class RecipeService {
    private final ObjectMapper objectMapper;
    private final RecipeMapper recipeMapper;
    private final CategoryService categoryService;
    private final RecipeRepository recipeRepository;
    private final IngredientService ingredientService;
    private final FileStorageService fileStorageService;
    private final RecipeIngredientMapper recipeIngredientMapper;

//    Această metodă primește o imagine de tip MultipartFile și un șir de caractere requestData care conține datele
//    în format JSON ale unei rețete.
    public RecipeResponseDto create(MultipartFile image, String requestData) {
        if (image == null || image.isEmpty()) {
            throw new BadRequestException("Recipe image cannot be empty");
        }

        try {
//            Utilizează ObjectMapper pentru a parsa datele JSON din requestData într-un obiect de tipul RecipeCreateRequestDto.
            RecipeCreateRequestDto recipeCreateRequestDto = this.objectMapper.readValue(
                requestData,
                RecipeCreateRequestDto.class
            );

//            Stochează fișierul imagine utilizând serviciul FileStorageService, iar returnează numele fișierului salvat.
            String filename = this.fileStorageService.storeFile(image);

//            Setează URI-ul imaginii (numele fișierului) în obiectul recipeCreateRequestDto.
            recipeCreateRequestDto.setPhotoUri(filename);

//            Apelează metoda create (a doua metodă definită mai jos) pentru a crea efectiv rețeta, folosind datele din
//            recipeCreateRequestDto.
            return this.create(recipeCreateRequestDto);
        } catch (IOException e) {
            throw new BadRequestException("Error appeared when creating recipe: " + e.getMessage());
        }
    }

    public RecipeResponseDto create(RecipeCreateRequestDto recipeCreateRequestDto) {
        if(this.recipeRepository.findByName(recipeCreateRequestDto.getName()).isPresent()){
            throw new ConflictException("There is already a recipe with this name");
        }

        CategoryModel categoryModel = this.categoryService.getModelById(recipeCreateRequestDto.getCategoryId());

        RecipeModel recipeModel = this.recipeMapper.toModel(recipeCreateRequestDto);

        recipeModel.setCategory(categoryModel);

        List<RecipeIngredientModel> recipeIngredientModels = new ArrayList<>();

        for(RecipeIngredientCreateRequestDto recipeIngredientCreateRequestDto: recipeCreateRequestDto.getRecipeIngredients()) {
            RecipeIngredientModel recipeIngredientModel = this.recipeIngredientMapper.toModel(
                recipeIngredientCreateRequestDto
            );

            IngredientModel ingredientModel = this.ingredientService.getModelById(
                recipeIngredientCreateRequestDto.getIngredientId()
            );

            recipeIngredientModel.setRecipe(recipeModel);
            recipeIngredientModel.setIngredient(ingredientModel);

            recipeIngredientModels.add(recipeIngredientModel);
        }

        recipeModel.setRecipeIngredients(recipeIngredientModels);

        RecipeModel savedRecipeModel = this.recipeRepository.save(recipeModel);

        return this.recipeMapper.toDto(savedRecipeModel);
    }

    @Transactional
    public RecipeResponseDto updateById(UUID id, MultipartFile image, String requestData) {
        RecipeModel recipeModel = this.recipeRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Nu exista nicio reteta cu ID-ul " + id));

        try {
            RecipeUpdateRequestDto recipeUpdateRequestDto = this.objectMapper.readValue(
                requestData,
                RecipeUpdateRequestDto.class
            );

            // Updatam imagine
            if(!image.isEmpty()) {
                String filename = this.fileStorageService.storeFile(image);
                recipeModel.setPhotoUri(filename);
            }

            // Updatam recipe
            recipeModel.setName(recipeUpdateRequestDto.getName());

            CategoryModel category = this.categoryService.getModelById(recipeUpdateRequestDto.getCategoryId());

            recipeModel.setCategory(category);
            recipeModel.setDescription(recipeUpdateRequestDto.getDescription());
            recipeModel.setPreparationTime(recipeUpdateRequestDto.getPreparationTime());

            // Updatam ingredientele doar daca nu sunt nule
            if (recipeUpdateRequestDto.getRecipeIngredients() != null) {

//                Facem un Map cu ingredientele existente in recipe, cheile fiind id-ul si val RecipeIngredientModel
                Map<UUID, RecipeIngredientModel> existingIngredients = new HashMap<>();
                for (RecipeIngredientModel ri : recipeModel.getRecipeIngredients()) {
                    existingIngredients.put(ri.getIngredient().getId(), ri);
                }

//                Fcaem un Set care va contine id-urile ingredientelor din request din frontend
                Set<UUID> requestIngredientIds = new HashSet<>();
                for (RecipeIngredientCreateRequestDto ri : recipeUpdateRequestDto.getRecipeIngredients()) {
                    requestIngredientIds.add(ri.getIngredientId());
                }


//                Inlaturam ingredientele care nu sunt in request
                recipeModel.getRecipeIngredients().removeIf(ri -> !requestIngredientIds.contains(ri.getIngredient().getId()));


//                Updatam ingredientele existente sau adaugam unele noi
                for (RecipeIngredientCreateRequestDto dto : recipeUpdateRequestDto.getRecipeIngredients()) {
                    UUID ingredientId = dto.getIngredientId();

                    if (existingIngredients.containsKey(ingredientId)) {
                        // Updatam ingredientul existent
                        RecipeIngredientModel ri = existingIngredients.get(ingredientId);
                        ri.setQuantity(dto.getQuantity());
                    } else {
                        // Adaugam un nou ingredient
                        RecipeIngredientModel newRi = this.recipeIngredientMapper.toModel(dto);
                        IngredientModel ingredientModel = this.ingredientService.getModelById(ingredientId);
                        newRi.setRecipe(recipeModel);
                        newRi.setIngredient(ingredientModel);
                        recipeModel.getRecipeIngredients().add(newRi);
                    }
                }
            }

            RecipeModel updatedRecipeModel = this.recipeRepository.save(recipeModel);
            return this.recipeMapper.toDto(updatedRecipeModel);
        } catch (IOException e) {
            throw new BadRequestException("Error appeared when creating recipe: " + e.getMessage());
        }
    }

    @Transactional
    public List<RecipeResponseDto> getAll(){
        List<RecipeModel> models = this.recipeRepository.findAll();

        List<RecipeResponseDto> responseDtos = new ArrayList<>();

        for(RecipeModel model: models){
            RecipeResponseDto responseDto = this.recipeMapper.toDto(model);
            responseDtos.add(responseDto);
        }

        return responseDtos;
    }

    @Transactional
    public RecipeResponseDto getById(UUID id){
        RecipeModel recipeModel = this.recipeRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Nu exista nicio reteta cu ID-ul " + id));

        return this.recipeMapper.toDto(recipeModel);
    }

    public void deleteAll(){this.recipeRepository.deleteAll();}

    public void deleteById(UUID id){
        this.recipeRepository.deleteById(id);
    }
}
