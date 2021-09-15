package cz.mavenclu.cookbook.rest.controller;

import cz.mavenclu.cookbook.dto.FeederDto;
import cz.mavenclu.cookbook.dto.FeederResponseDto;
import cz.mavenclu.cookbook.service.FeederService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@Slf4j
@RestController
public class FeederRestController implements FeederApi{

    private final FeederService feederService;

    public FeederRestController(FeederService feederService) {
        this.feederService = feederService;
    }

    @Override
    public FeederResponseDto addNewFeeder(FeederDto feeder) {
        log.info("addNewFeeder() - adding feeder with param: {}", feeder);
        return feederService.addNewFeeder(feeder);

    }

    @Override
    public FeederResponseDto updateFeedersName(Long id, FeederDto feeder) {
        return feederService.updateFeedersName(id, feeder);
    }

    @Override
    public List<FeederResponseDto> getAllFeeders() {
        return feederService.findAll();
    }

    @Override
    public FeederResponseDto getFeeder(Long id) {
        return feederService.findFeeder(id);
    }

    @Override
    public void delete(Long id) {
        feederService.deleteFeeder(id);
    }
}
