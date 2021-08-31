package cz.mavenclu.cookbook.rest.controller;

import cz.mavenclu.cookbook.dto.RecipeDto;
import cz.mavenclu.cookbook.dto.RecipeResponseDto;
import cz.mavenclu.cookbook.entity.Recipe;
import cz.mavenclu.cookbook.service.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@Slf4j
@RestController
public class RecipeRestController implements RecipeRestApi{

    private final RecipeService recipeService;

    public RecipeRestController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }


    @Override
    public RecipeResponseDto addNewRecipe(RecipeDto recipeDto) {
        Recipe recipe = recipeService.addRecipe(recipeDto);
        return recipeService.getRecipe(recipe.getId());
    }

    @Override
    public RecipeResponseDto updateRecipe(Long id, RecipeDto recipeDto) {
        try {
            recipeService.updateRecipe(recipeDto, recipeService.getById(id));
            return recipeService.getRecipe(id);
        } catch (MethodArgumentTypeMismatchException | NumberFormatException argumentTypeMismatchException) {
            log.error("updateRecipe() - caught MethodArgumentTypeMismatchException");
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Bad input", argumentTypeMismatchException
            );
        }
    }

    @Override
    public RecipeResponseDto getRecipe(Long id) {
        try {
            log.info("getRecipe() - find recipe with ID: {}", id);
            return recipeService.getRecipe(id);
        } catch (MethodArgumentTypeMismatchException | NumberFormatException argumentTypeMismatchException) {
            log.error("getRecipe() - caught MethodArgumentTypeMismatchException");
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Bad input", argumentTypeMismatchException
            );
        }
    }

    @Override
    public List<RecipeResponseDto> getAllRecipes() {
        return recipeService.getAllRecipes();
    }
}
