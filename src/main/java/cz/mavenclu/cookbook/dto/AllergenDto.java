package cz.mavenclu.cookbook.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Allergen")
public class AllergenDto {

    @Schema(description = "name of the allergen")
    private String name;

    @Schema(description = "code of the allergen")
    private int code;
}
