package cz.mavenclu.cookbook.mapper;

import cz.mavenclu.cookbook.dto.RecipeDto;
import cz.mavenclu.cookbook.dto.RecipeItemDto;
import cz.mavenclu.cookbook.dto.RecipeItemResponseDto;
import cz.mavenclu.cookbook.entity.Recipe;
import cz.mavenclu.cookbook.entity.RecipeItem;
import cz.mavenclu.cookbook.service.IngredientService;
import cz.mavenclu.cookbook.service.RecipeService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.WARN,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        uses = {IngredientService.class, RecipeService.class}

)
public interface RecipeItemMapper {

    @Mapping(target = "ingredient", source = "recipeItemDto.ingredientId")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "recipe", source = "recipe.id")
    RecipeItem mapToRecipeItem(RecipeItemDto recipeItemDto, Recipe recipe);


    @Mapping(target = "recipeId", source = "recipe.id")
    RecipeItemResponseDto mapToRecipeItemResponseDto(RecipeItem recipeItem);

    List<RecipeItemResponseDto> mapToRecipeItemResponseDtoList(List<RecipeItem> recipeItems);

    default List<RecipeItem> mapToRecipeItemList(RecipeDto recipeDto, Recipe recipe){
        return recipeDto.getIngredients().stream()
                .map(recipeItemDto -> mapToRecipeItem(recipeItemDto, recipe))
                .collect(Collectors.toList());
    }

}
