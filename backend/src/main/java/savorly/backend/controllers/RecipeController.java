package savorly.backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import savorly.backend.dtos.Requests.RecipeCreateRequestDto;
import savorly.backend.dtos.Requests.RecipeUpdateRequestDto;
import savorly.backend.dtos.responses.RecipeResponseDto;
import savorly.backend.services.FileStorageService;
import savorly.backend.services.RecipeService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recipes")
public class RecipeController {
    private final RecipeService recipeService;

//    Această valoare îi spune Spring-ului că endpoint-ul nostru va primi o cerere care include date de tip fișier
//    (fișierul încărcat, de obicei o imagine) și un JSON care reprezintă rețeta (date text). De asemenea, se specifică
//    că serverul trebuie să poată procesa fișierele încărcate în format multipart.
    @PostMapping(consumes = { "multipart/form-data" })
    public ResponseEntity<RecipeResponseDto> create(
        @RequestPart("image") MultipartFile image,
        @RequestPart("recipe") String recipeJson
    ) {
        RecipeResponseDto recipeResponseDto = this.recipeService.create(image, recipeJson);
        return ResponseEntity.ok(recipeResponseDto);
    }

    @PutMapping(value = "/{id}", consumes = { "multipart/form-data" })
    public ResponseEntity<RecipeResponseDto> updateById(
        @PathVariable UUID id,
        @RequestPart("image") MultipartFile image,
        @RequestPart("recipe") String recipeJson
    ){
        RecipeResponseDto recipeResponseDto = this.recipeService.updateById(id, image, recipeJson);
        return ResponseEntity.ok(recipeResponseDto);
    }

//     public ResponseEntity<List<RecipeResponseDto>> getAll (
//     @RequestParam Integer minPreparationTime,
//     @RequestParam Integer maxPreparationTime
//     ){
//     System.out.println(minPreparationTime) -> o sa ne afiseze cat am pus

    @GetMapping
    public ResponseEntity<List<RecipeResponseDto>> getAll (){
        List<RecipeResponseDto> recipeResponseDto = this.recipeService.getAll();
        return  ResponseEntity.ok(recipeResponseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeResponseDto> getById(@PathVariable UUID id){
        RecipeResponseDto recipeResponseDto = this.recipeService.getById(id);
        return  ResponseEntity.ok(recipeResponseDto);
    }

    @DeleteMapping
    public ResponseEntity<Map<String, String>> deleteAll(){
        this.recipeService.deleteAll();
        Map<String, String> messageDto = new HashMap<>();
        messageDto.put("message","All recipes have been deleted successfully");
        return ResponseEntity.ok(messageDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteById(@PathVariable UUID id){
        this.recipeService.deleteById(id);
        Map<String, String> messageDto = new HashMap<>();
        messageDto.put("message", "The recipe has been deleted successfully");
        return ResponseEntity.ok(messageDto);
    }
}
