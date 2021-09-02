package cz.mavenclu.cookbook.web.controller;

import cz.mavenclu.cookbook.web.dto.RecipeWebDto;
import cz.mavenclu.cookbook.web.service.RecipeWebService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.SQLGrammarException;
import org.postgresql.util.PSQLException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;
import org.thymeleaf.exceptions.TemplateInputException;

@Slf4j
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

    }
}
