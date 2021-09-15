package cz.mavenclu.cookbook.service;

import cz.mavenclu.cookbook.dao.FeederRepository;
import cz.mavenclu.cookbook.dto.FeederDto;
import cz.mavenclu.cookbook.dto.FeederResponseDto;
import cz.mavenclu.cookbook.entity.Feeder;
import cz.mavenclu.cookbook.exception.FeederNotFoundException;
import cz.mavenclu.cookbook.mapper.FeederMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class FeederService {

    private final FeederRepository feederRepo;
    private final FeederMapper feederMapper;

    public FeederService(FeederRepository feederRepo, FeederMapper feederMapper) {
        this.feederRepo = feederRepo;
        this.feederMapper = feederMapper;
    }

    public FeederResponseDto addNewFeeder(FeederDto feederDto) {
        log.info("addNewFeeder - adding with param: {}", feederDto);
        Feeder feeder = Feeder.builder().name(feederDto.getName()).build();
        log.info("addNewFeeder - created new entity Feeder with ID: {}", feeder.getId());
        feederRepo.save(feeder);
        log.info("addNewFeeder - saved newly created feeder with ID: {}", feeder.getId());
        log.info("addNewFeeder - mapping to response feeder");
        FeederResponseDto responseFeeder = feederMapper.mapToFeederResponseDto(feeder);
        log.info("addNewFeeder - returned: {}", responseFeeder);
        return responseFeeder;

    }
    public FeederResponseDto updateFeedersName(Long id, FeederDto feederDto) {
        log.info("addNewFeeder - updating feeder with ID: {} and updates: {}", id, feederDto);
        log.info("addNewFeeder - calling findById()");
        Feeder feeder = findById(id);
        feeder.setName(feederDto.getName());
        feederRepo.save(feeder);
        log.info("addNewFeeder - updated feeder: {}", feeder);
        FeederResponseDto responseFeeder = feederMapper.mapToFeederResponseDto(feeder);
        log.info("addNewFeeder - got: {}", responseFeeder);
        return responseFeeder;
    }


    private Feeder findById(Long id){
        log.info("findById - looking for Feeder with ID: {}", id);
        Feeder feeder = feederRepo.findById(id).orElseThrow(() -> new FeederNotFoundException(id));
        log.info("findById - found feeder with ID: {}", id);
        return feeder;
    }

    public List<FeederResponseDto> findAll() {
        return feederMapper.mapToFeederResponseDtoList(feederRepo.findAll());
    }
}
