package savorly.backend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import savorly.backend.dtos.Requests.IngredientCreateRequestDto;
import savorly.backend.dtos.Requests.IngredientUpdateRequestDto;
import savorly.backend.dtos.responses.IngredientResponseDto;
import savorly.backend.exceptions.ConflictException;
import savorly.backend.exceptions.NotFoundException;
import savorly.backend.models.IngredientModel;
import savorly.backend.repositories.IngredientRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IngredientService {
    private final IngredientRepository repository;

    public IngredientResponseDto create(IngredientCreateRequestDto requestDto) {
        if (this.repository.findByName(requestDto.getName()).isPresent()) {
            throw new ConflictException("There is already an ingredient with this name");
        }

        // Create an IngredientModel object
        IngredientModel ingredientModel = new IngredientModel();
        // Populate it with data from the CreateDto
        ingredientModel.setName(requestDto.getName());
        // Save to Repository and get the response (another Model)
        IngredientModel createdIngredientModel = this.repository.save(ingredientModel);
        // Create a ResponseDto object
        IngredientResponseDto ingredientResponseDto = new IngredientResponseDto();
        // Populate the ResponseDto with data from the Model received from Repository
        ingredientResponseDto.setId(createdIngredientModel.getId());
        ingredientResponseDto.setName(createdIngredientModel.getName());
        // Return the response
        return ingredientResponseDto;
    }

    public List<IngredientResponseDto> getAll() {
        List<IngredientModel> ingredientModels = this.repository.findAll();
        List<IngredientResponseDto> ingredientResponseDtos = new ArrayList<>();

        for (IngredientModel ingredientModel : ingredientModels) {
            IngredientResponseDto ingredientResponseDto = new IngredientResponseDto();
            ingredientResponseDto.setId(ingredientModel.getId());
            ingredientResponseDto.setName(ingredientModel.getName());
            ingredientResponseDtos.add(ingredientResponseDto);
        }

        return ingredientResponseDtos;
    }

    public IngredientResponseDto getById(UUID id) {
        Optional<IngredientModel> optionalIngredientModel = this.repository.findById(id);

        if (optionalIngredientModel.isEmpty()) {
            return null;
        }

        IngredientModel ingredientModel = optionalIngredientModel.get();

        IngredientResponseDto ingredientResponseDto = new IngredientResponseDto();
        ingredientResponseDto.setId(ingredientModel.getId());
        ingredientResponseDto.setName(ingredientModel.getName());
        return ingredientResponseDto;
    }

    // vreau o metoda denumita getOrCreate
    // ea va crea un ingredient daca nu exista, sau il va returna daca exista
    public IngredientModel getOrCreate(IngredientCreateRequestDto requestDto){
        String name = requestDto.getName();
        return this.repository.findByName(name).orElseGet(() -> {
            IngredientModel ingredientModel = new IngredientModel();
            ingredientModel.setName(name);
            ingredientModel = this.repository.save(ingredientModel);
            return ingredientModel;
        });
    }

    public IngredientModel getModelById(UUID id) {
        return this.repository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Could not find ingredient with this ID"));
    }

    public void deleteAll() {
        this.repository.deleteAll();
    }

    public void deleteById(UUID id) {
        this.repository.deleteById(id);
    }

    public IngredientResponseDto updateById(UUID id, IngredientUpdateRequestDto ingredientUpdateRequestDto) {
        IngredientModel ingredientModel = this.repository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("No ingredient found with ID " + id));

        ingredientModel.setName(ingredientUpdateRequestDto.getName());

        IngredientModel updatedIngredientModel = repository.save(ingredientModel);

        IngredientResponseDto ingredientResponseDto = new IngredientResponseDto();
        ingredientResponseDto.setId(updatedIngredientModel.getId());
        ingredientResponseDto.setName(updatedIngredientModel.getName());

        return ingredientResponseDto;
    }
}