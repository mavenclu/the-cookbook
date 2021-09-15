package cz.mavenclu.cookbook.thymeleaf.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeItemForm {

    private String amount;
    private RecipeItemWebDto.Measure measure;
    private String ingredientId;
}
