package cz.mavenclu.cookbook.dto;

import cz.mavenclu.cookbook.entity.Allergen;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Schema(name = "Feeder")
@Validated
@Data
public class FeederDto {


    @Schema(description = "first name of a feeder")
    @NotNull
    @Size(min = 2, max = 20)
    private String name;

    @Schema(description = "chef's ID")
    @NotNull
    private String chefId;

    @Schema(description = "list of feeders allergens")
    private List<Allergen> allergens;

    @Schema(description = "list of feeders ingredient intolerances")
    private List<IngredientDto> intolerances;

}
