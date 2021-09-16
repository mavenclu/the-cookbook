package cz.mavenclu.cookbook.dto;

import cz.mavenclu.cookbook.entity.Allergen;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
@Data
@Schema(name = "Feeder Response DTO")
public class FeederResponseDto {

    @Schema(description = "feeder's ID")
    private long id;
    @Schema(description = "feeder's name")
    private String name;
    @Schema(description = "ID of a feeder's chef")
    private String chefId;
    @Schema(description = "list of feeders allergens")
    private List<Allergen> allergens;
    @Schema(description = "list of feeders ingredient intolerances")
    private List<IngredientDto> intolerances;

}
