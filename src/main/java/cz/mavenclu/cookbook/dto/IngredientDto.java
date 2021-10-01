package cz.mavenclu.cookbook.dto;

import cz.mavenclu.cookbook.entity.Ingredient;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Ingredient")
public class IngredientDto {

    private String name;
    private String description;
    private Ingredient.IngredientStatus status;

}
