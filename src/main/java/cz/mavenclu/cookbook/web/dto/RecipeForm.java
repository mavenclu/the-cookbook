package cz.mavenclu.cookbook.web.dto;

import cz.mavenclu.cookbook.entity.Recipe;
import cz.mavenclu.cookbook.entity.RecipeItem;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
public class RecipeForm {
    @NotNull
    @Size(min = 3, max = 90, message = "title should contain at least 3 characters and at most 90")
    private String title;
    @Size(min = 0, max = 160, message = "recipe description should be at most 160 characters long")
    private String description;
    private int prepTime;
    private int cookingTime;
    private Recipe.Difficulty difficulty;
    private Recipe.Cuisine cuisine;
    private List<Recipe.Diet> diets;

    List<RecipeItem> ingredients = new ArrayList<>();
    List<String> instructions = new ArrayList<>();




}
