package cz.mavenclu.cookbook.dto;

import cz.mavenclu.cookbook.entity.Recipe;
import cz.mavenclu.cookbook.entity.RecipeItem;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Schema(name = "Recipe")
@Validated
public class RecipeDto {

    @NotNull
    @Size(min = 3, max = 90)
    @Schema(description = "recipe's title")
    private String title;

    @NotNull
    @Size(min = 5, max = 160)
    @Schema(description = "shortly describe the recipe")
    private String description;

    @NotNull
    @Min(0)
    @Schema(description = "how many minutes it takes to prepare for the cooking")
    private int prepTime;
    @NotNull
    @Min(0)
    @Schema(description = "how many minutes it takes to cook the meal")
    private int cookingTime;

    @NotNull
    @Schema(description = "how difficult it is to cook the meal? hard, easy, ok?")
    private Recipe.Difficulty difficulty;

    @NotNull
    @Schema(description = "to which cuisine recipe belongs to")
    private Recipe.Cuisine cuisine;
    @NotNull
    @Schema(description = "to what kind of diet recipe belongs to")
    private List<Recipe.Diet> diets;

    @NotNull
    @Schema(description = "list of ingredients")
    private List<RecipeItemDto> ingredients;

    @NotNull
    @Schema(description = "recipe instructions")
    List<String> instructions;

}