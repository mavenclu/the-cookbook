package cz.mavenclu.cookbook.web.controller;

import cz.mavenclu.cookbook.web.dto.RecipeWebDto;
import cz.mavenclu.cookbook.web.service.RecipeWebService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Mono;

@Controller
public class RecipeMvcController {

    private final RecipeWebService recipeWebService;

    public RecipeMvcController(RecipeWebService recipeWebService) {
        this.recipeWebService = recipeWebService;
    }

    @GetMapping("/recipe/add")
    public String renderRecipeForm(Model model){
       addAttributesToModelAddRecipeForm(model);
        return "add-recipe-form";
    }

    @GetMapping("/recipes/{id}")
    public String getRecipe(Model model, @PathVariable Long id){
        RecipeWebDto recipe = recipeWebService.getRecipe(id).block();
        model.addAttribute("recipe", recipe);
        return "recipe";
    }

    private void addAttributesToModelAddRecipeForm(Model model){

    }
}
