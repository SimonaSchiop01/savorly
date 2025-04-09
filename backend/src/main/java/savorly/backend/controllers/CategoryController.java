package savorly.backend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import savorly.backend.dtos.Requests.CategoryCreateRequestDto;
import savorly.backend.dtos.Requests.CategoryUpdateRequestDto;
import savorly.backend.dtos.responses.CategoryResponseDto;
import savorly.backend.services.CategoryService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDto> create(@RequestBody CategoryCreateRequestDto recipeTypeCreateRequestDto){
         CategoryResponseDto recipeTypeResponseDto = this.categoryService.create(recipeTypeCreateRequestDto);
         return ResponseEntity.ok(recipeTypeResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> getAll(){
        List<CategoryResponseDto> recipeTypeResponseDto = this.categoryService.getAll();
        return ResponseEntity.ok(recipeTypeResponseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> getById(@PathVariable UUID id){
         CategoryResponseDto recipeTypeResponseDto = this.categoryService.getDtoById(id);
         return ResponseEntity.ok(recipeTypeResponseDto);
    }

    @DeleteMapping
    public ResponseEntity<Map<String, String>> deleteAll(){
          this.categoryService.deleteAll();
          Map<String, String> messageDto = new HashMap<>();
          messageDto.put("message", "All recipe types have been deleted successfully");
          return ResponseEntity.ok(messageDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteById(@PathVariable UUID id){
        this.categoryService.deleteById(id);
        Map<String, String> messageDto = new HashMap<>();
        messageDto.put("message", "The recipe type has been deleted successfully");
        return ResponseEntity.ok(messageDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> updateById(
            @PathVariable UUID id,
            @RequestBody CategoryUpdateRequestDto recipeTypeUpdateRequestDto
    ){
        CategoryResponseDto recipeTypeResponseDto = this.categoryService.updateById(id, recipeTypeUpdateRequestDto);
        return ResponseEntity.ok(recipeTypeResponseDto);
    }
}
