package cz.mavenclu.cookbook.dto;

import cz.mavenclu.cookbook.entity.Recipe;
import cz.mavenclu.cookbook.rest.controller.RecipeRestController;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class FilterDto {

    private Recipe.Difficulty difficulty;
    private Recipe.Diet diet;
    private Recipe.Cuisine cuisine;
    private List<FeederResponseDto> feeders;
    private Recipe.RequiredTimeInterval requiredTime;
}
