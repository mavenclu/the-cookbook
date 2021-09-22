package cz.mavenclu.cookbook.mapper;

import cz.mavenclu.cookbook.dto.FeederResponseDto;
import cz.mavenclu.cookbook.entity.Feeder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(
        componentModel = "spring"
)
public interface FeederMapper {

    @Mapping(target = "intolerances", source = "ingredientIntolerances")
    FeederResponseDto mapToFeederResponseDto(Feeder feeder);

    List<FeederResponseDto> mapToFeederResponseDtoList(List<Feeder> all);
}
