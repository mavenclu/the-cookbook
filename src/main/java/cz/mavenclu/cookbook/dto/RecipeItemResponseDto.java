package cz.mavenclu.cookbook.dto;

import cz.mavenclu.cookbook.entity.RecipeItem;
import lombok.Data;

@Data
public class RecipeItemResponseDto {

    private String amount;
    private RecipeItem.Measure measure;
    private IngredientResponseDto ingredient;
    private long recipeId;

}
