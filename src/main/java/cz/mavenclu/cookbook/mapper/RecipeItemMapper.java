package cz.mavenclu.cookbook.mapper;

import cz.mavenclu.cookbook.dao.RecipeRepository;
import cz.mavenclu.cookbook.dto.RecipeDto;
import cz.mavenclu.cookbook.dto.RecipeItemDto;
import cz.mavenclu.cookbook.dto.RecipeItemResponseDto;
import cz.mavenclu.cookbook.dto.RecipeResponseDto;
import cz.mavenclu.cookbook.entity.Recipe;
import cz.mavenclu.cookbook.entity.RecipeItem;
import cz.mavenclu.cookbook.service.RecipeService;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.WARN,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        uses = {IngredientMapper.class, RecipeService.class}

)
public interface RecipeItemMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "recipe", source = "recipe.id")
    RecipeItem mapToRecipeItem(RecipeItemDto recipeItemDto, Recipe recipe);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "recipe", source = "recipe.id")
    RecipeItem updateFromRecipeItemDto(RecipeItemDto recipeItemDto, Recipe recipe, @MappingTarget RecipeItem recipeItem);

    @Named(value = "toRecipeItemDto")
    @Mapping(target = "ingredient", qualifiedByName = "toIngredientDto")
    RecipeItemDto mapToRecipeItemDto(RecipeItem recipeItem);

    @Mapping(target = "recipeId", source = "recipe.id")
    RecipeItemResponseDto mapToRecipeItemResponseDto(RecipeItem recipeItem);

    @IterableMapping(qualifiedByName = "toRecipeItemDto")
    List<RecipeItemDto> mapToRecipeItemDtoList(List<RecipeItem> recipeItems);

    default List<RecipeItem> mapToRecipeItemList(RecipeDto recipeDto, Recipe recipe){
        return recipeDto.getIngredients().stream()
                .map(recipeItemDto -> mapToRecipeItem(recipeItemDto, recipe))
                .collect(Collectors.toList());
    }


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "recipe", ignore = true)
    RecipeItem updateFromRecipeItemDto(RecipeItemDto recipeItemDto, @MappingTarget RecipeItem recipeItem);
}
