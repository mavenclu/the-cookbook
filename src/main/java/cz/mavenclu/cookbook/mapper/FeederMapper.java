package cz.mavenclu.cookbook.mapper;

import cz.mavenclu.cookbook.dto.FeederResponseDto;
import cz.mavenclu.cookbook.entity.Feeder;
import cz.mavenclu.cookbook.service.FeederService;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = {FeederService.class}
)
public interface FeederMapper {

    FeederResponseDto mapToFeederResponseDto(Feeder feeder);

    List<FeederResponseDto> mapToFeederResponseDtoList(List<Feeder> all);
}
