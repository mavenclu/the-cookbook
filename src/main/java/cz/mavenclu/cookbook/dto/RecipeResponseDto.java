package cz.mavenclu.cookbook.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(name = "Recipe Response DTO")
public class RecipeResponseDto extends RecipeDto{

    private long id;
}
