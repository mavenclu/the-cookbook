package cz.mavenclu.cookbook.rest.controller;

import cz.mavenclu.cookbook.dto.IngredientDto;
import cz.mavenclu.cookbook.dto.IngredientResponseDto;
import cz.mavenclu.cookbook.service.IngredientService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IngredientRestController implements IngredientRestApi {

    private final IngredientService ingredientService;

    public IngredientRestController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @Override
    public List<IngredientResponseDto> getAllIngredients() {
        return ingredientService.getAll();
    }

    @Override
    public IngredientResponseDto getIngredient(Long id) {
        return ingredientService.getIngredient(id);
    }

    @Override
    public IngredientResponseDto addIngredient(IngredientDto ingredientDto) {
        return ingredientService.addIngredient(ingredientDto);
    }

    @Override
    public IngredientResponseDto updateIngredient(IngredientDto ingredientDto, Long id) {
        return ingredientService.updateIngredient(ingredientDto, id);
    }
}
