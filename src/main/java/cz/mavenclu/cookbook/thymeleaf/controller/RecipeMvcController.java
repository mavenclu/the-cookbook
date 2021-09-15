package cz.mavenclu.cookbook.thymeleaf.controller;

import cz.mavenclu.cookbook.thymeleaf.dto.IngredientWebDto;
import cz.mavenclu.cookbook.thymeleaf.dto.RecipeForm;
import cz.mavenclu.cookbook.thymeleaf.dto.RecipeItemForm;
import cz.mavenclu.cookbook.thymeleaf.dto.RecipeItemWebDto;
import cz.mavenclu.cookbook.thymeleaf.dto.RecipeWebDto;
import cz.mavenclu.cookbook.thymeleaf.service.IngredientWebService;
import cz.mavenclu.cookbook.thymeleaf.service.RecipeWebService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;
import org.thymeleaf.exceptions.TemplateInputException;

import java.util.List;

@Slf4j
@Controller
public class RecipeMvcController {

    private final RecipeWebService recipeWebService;
    private final IngredientWebService ingredientWebService;

    public RecipeMvcController(RecipeWebService recipeWebService, IngredientWebService ingredientWebService) {
        this.recipeWebService = recipeWebService;
        this.ingredientWebService = ingredientWebService;
    }

    @GetMapping("/recipes/add")
    public String renderRecipeForm(Model model,  @AuthenticationPrincipal OidcUser principal){
        ControllerCommonUtil.addProfileToModel(model, principal);
        log.info("renderRecipeForm() - creating form");
        addAttributesToModelAddRecipeForm(model);
        log.info("renderForm() - added attributes to form. Form rendered.");
        return "add-recipe-form";
    }


    @PostMapping("/recipes/save")
    public String saveRecipe(@ModelAttribute RecipeForm recipeForm, Model model, BindingResult result,  @AuthenticationPrincipal OidcUser principal){
        log.info("saveRecipe() - checking for errors");
        ControllerCommonUtil.addProfileToModel(model, principal);
        if (result.hasErrors()) {
            log.warn("saveRecipe() - errors found: {}", result.getAllErrors());
            model.addAttribute("errors", result.getAllErrors());
            return "error";
        }else {
            log.info("saveRecipe() - no errors. calling RecipeWebService to save recipe");
            recipeWebService.saveRecipe(recipeForm);
            log.info("saveRecipe() - recipe saved.");
            model.addAttribute("recipes", recipeWebService.getAllRecipes());
            log.info("saveRecipe() - got all recipes from db, added to model, redirecting to homepage");
            return "redirect:/";
        }

    }

    @GetMapping("/recipe-post")
    public String showRecipe(Model model){
        return "recipe-post";
    }

    @GetMapping("/recipes/{id}")
    public String showRecipeTemplate(Model model, @PathVariable Long id){
        try {

            log.info("showRecipeTemplate() - populating template by recipe with ID: {}", id);
            RecipeWebDto recipe = recipeWebService.getRecipeWithWebClient(id);
            log.info("showRecipeTemplate() - got recipe: {}", recipe);
            log.info("showRecipeTemplate() - adding recipe to model");
            model.addAttribute("recipe", recipe);
            log.info("showRecipeTemplate() - rendering template");
            return "recipe-post";
        } catch (WebClientResponseException | TemplateInputException clientResponseException) {
            log.error("showRecipeTemplate() - caught PSQL Exception {}", clientResponseException.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Failed to render template", clientResponseException
            );
        }
    }

    private void addAttributesToModelAddRecipeForm(Model model){
        RecipeForm recipe = new RecipeForm();
        recipe.getInstructions().add("");
        recipe.getInstructions().add("");
        recipe.getInstructions().add("");
        recipe.getIngredients().add(new RecipeItemForm());
        recipe.getIngredients().add(new RecipeItemForm());
        recipe.getIngredients().add(new RecipeItemForm());

        model.addAttribute("recipe", recipe);
        model.addAttribute("cuisine", RecipeWebDto.Cuisine.values());
        model.addAttribute("diet", RecipeWebDto.Diet.values());
        model.addAttribute("difficulty", RecipeWebDto.Difficulty.values());
        model.addAttribute("measure", RecipeItemWebDto.Measure.values());
        List<IngredientWebDto> ingredients = ingredientWebService.getAllIngredients();
        model.addAttribute("ingredientsFromDb", ingredients);

    }

}
