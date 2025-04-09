package savorly.backend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import savorly.backend.dtos.Requests.IngredientCreateRequestDto;
import savorly.backend.dtos.Requests.IngredientUpdateRequestDto;
import savorly.backend.services.IngredientService;
import savorly.backend.dtos.responses.IngredientResponseDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/ingredients")
public class IngredientController {
    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @PostMapping
    public ResponseEntity<IngredientResponseDto> create(@RequestBody IngredientCreateRequestDto ingredientCreateRequestDto){
        IngredientResponseDto ingredientResponseDto = this.ingredientService.create(ingredientCreateRequestDto);
        return ResponseEntity.ok(ingredientResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<IngredientResponseDto>> getAll(){
        List<IngredientResponseDto> ingredientResponseDto = this.ingredientService.getAll();
        return  ResponseEntity.ok(ingredientResponseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IngredientResponseDto> getById(@PathVariable UUID id){
        IngredientResponseDto ingredientResponseDto = this.ingredientService.getById(id);
        return  ResponseEntity.ok(ingredientResponseDto);
    }

    @DeleteMapping
    public ResponseEntity<Map<String, String>> deleteAll(){
        this.ingredientService.deleteAll();
        Map<String, String> messageDto = new HashMap<>();
        messageDto.put("message", "All ingredients have been deleted successfully");
        return ResponseEntity.ok(messageDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteById(@PathVariable UUID id){
        this.ingredientService.deleteById(id);
        Map<String, String> messageDto = new HashMap<>();
        messageDto.put("message", "The ingredient has been deleted successfully");
        return ResponseEntity.ok(messageDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<IngredientResponseDto> updateById(@PathVariable UUID id,
                                                            @RequestBody IngredientUpdateRequestDto ingredientUpdateRequestDto){
        IngredientResponseDto ingredientResponseDto = this.ingredientService.updateById(id, ingredientUpdateRequestDto);
        return ResponseEntity.ok(ingredientResponseDto);
    }
}