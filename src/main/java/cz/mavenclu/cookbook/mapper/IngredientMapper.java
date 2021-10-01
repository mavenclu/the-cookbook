package cz.mavenclu.cookbook.mapper;

import cz.mavenclu.cookbook.dto.IngredientDto;
import cz.mavenclu.cookbook.dto.IngredientResponseDto;
import cz.mavenclu.cookbook.entity.Ingredient;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.WARN,
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface IngredientMapper {

    @Mapping(target = "allergen", ignore = true)
    @Mapping(target = "status", expression = "java(Ingredient.IngredientStatus.PROPOSED)")
    @Mapping(target = "id", ignore = true)
    Ingredient mapToIngredient(IngredientDto ingredientDto);

    @Named(value = "toIngredientDto")
    IngredientDto mapToIngredientDto(Ingredient ingredient);

    @Named(value = "toIngredientResponseDto")
    IngredientResponseDto mapToIngredientResponseDto(Ingredient ingredient);

    @IterableMapping(qualifiedByName = "toIngredientResponseDto")
    List<IngredientResponseDto> mapToIngredientResponseDtoList(List<Ingredient> ingredients);

    @Mapping(target = "id", ignore = true)
    void updateFromIngredientDto(IngredientDto ingredientDto, @MappingTarget Ingredient ingredient);

}
