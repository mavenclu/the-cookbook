package cz.mavenclu.cookbook.mapper;

import cz.mavenclu.cookbook.dto.FeederDto;
import cz.mavenclu.cookbook.dto.FeederResponseDto;
import cz.mavenclu.cookbook.entity.Feeder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(
        componentModel = "spring"
)
public interface FeederMapper {

    @Mapping(target = "intolerances", source = "ingredientIntolerances")
    FeederResponseDto mapToFeederResponseDto(Feeder feeder);

    List<FeederResponseDto> mapToFeederResponseDtoList(List<Feeder> all);

    //todo po pridani funcionality vymazat ignore, a udelat mapovani
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "favoriteRecipes", ignore = true)
    Feeder mapToFeeder(FeederDto feederDto);

    @Mapping(target = "ingredientIntolerances", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "favoriteRecipes", ignore = true)
    @Mapping(target = "allergens", ignore = true)
    void updateFromFeederDto(FeederDto feederDto, @MappingTarget Feeder feeder);
}
