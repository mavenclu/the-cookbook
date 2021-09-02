package cz.mavenclu.cookbook.dto;

import cz.mavenclu.cookbook.entity.RecipeItem;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Recipe Ingredient")
public class RecipeItemDto {

    private String amount;
    private RecipeItem.Measure measure;
    private IngredientDto ingredient;
}
