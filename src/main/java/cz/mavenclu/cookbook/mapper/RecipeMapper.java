package cz.mavenclu.cookbook.mapper;

import cz.mavenclu.cookbook.dao.RecipeItemRepository;
import cz.mavenclu.cookbook.dao.RecipeRepository;
import cz.mavenclu.cookbook.dto.RecipeDto;
import cz.mavenclu.cookbook.dto.RecipeItemDto;
import cz.mavenclu.cookbook.dto.RecipeItemResponseDto;
import cz.mavenclu.cookbook.dto.RecipeResponseDto;
import cz.mavenclu.cookbook.entity.Recipe;
import cz.mavenclu.cookbook.entity.RecipeItem;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Mapper(
        componentModel = "spring",
        uses = {RecipeRepository.class, RecipeItemRepository.class,
                IngredientMapper.class, RecipeItemMapper.class},
        unmappedSourcePolicy = ReportingPolicy.WARN,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface   RecipeMapper {

    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "archived", ignore = true)
    @Mapping(target = "recipeItems", source = "ingredients")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cuisine", source = "cuisine", defaultValue = "OTHER")
    Recipe mapToRecipe(RecipeDto recipeDto);


    @Mapping(target = "ingredients", source = "recipeItems")
    RecipeResponseDto mapToRecipeResponseDto(Recipe recipe);

    List<RecipeResponseDto> mapToRecipeResponseDtoList(List<Recipe> recipes);



    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "archived", ignore = true)
    @Mapping(target = "recipeItems", source = "ingredients")
    @Mapping(target = "id", ignore = true)
    Recipe updateFromRecipeDto(RecipeDto recipeDto, @MappingTarget Recipe recipe);

}
