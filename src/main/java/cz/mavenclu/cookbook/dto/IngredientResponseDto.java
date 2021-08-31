package cz.mavenclu.cookbook.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Ingredient Response DTO")
public class IngredientResponseDto extends IngredientDto{

    private long id;
}
