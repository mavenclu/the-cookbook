package cz.mavenclu.cookbook.thymeleaf.controller;

import cz.mavenclu.cookbook.thymeleaf.service.RecipeWebService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    private final RecipeWebService recipeWebService;

    public HomeController(RecipeWebService recipeWebService) {
        this.recipeWebService = recipeWebService;
    }

    @RequestMapping("/")
    public String home(Model model) {
        model.addAttribute("recipes", recipeWebService.getAllRecipes());
        return "index";
    }

}