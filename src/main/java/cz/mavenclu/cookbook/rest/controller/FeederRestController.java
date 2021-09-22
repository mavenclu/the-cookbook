package cz.mavenclu.cookbook.rest.controller;

import cz.mavenclu.cookbook.dto.ChefDto;
import cz.mavenclu.cookbook.dto.FeederDto;
import cz.mavenclu.cookbook.dto.FeederResponseDto;
import cz.mavenclu.cookbook.entity.Allergen;
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
    public FeederResponseDto addNewFeeder(FeederDto feeder, ChefDto chef) {
        log.info("addNewFeeder() - adding feeder with param: {}", feeder);
        return feederService.addNewFeeder(feeder, chef);

    }

    @Override
    public FeederResponseDto updateFeedersName(Long id, FeederDto feeder, ChefDto chef) {
        return feederService.updateFeedersName(id, feeder, chef);
    }

    @Override
    public List<FeederResponseDto> getAllFeeders(ChefDto chef) {
        return feederService.findAll(chef);
    }

    @Override
    public FeederResponseDto getFeeder(Long id, ChefDto chef) {
        return feederService.findFeeder(id, chef);
    }

    @Override
    public void delete(Long id, ChefDto chef) {
        feederService.deleteFeeder(id, chef);
    }

    @Override
    public void addAllergen(Long id, Allergen allergen, ChefDto chef) {
        feederService.addAllergen(id, allergen, chef);
    }

    @Override
    public void removeAllergen(Long id, Allergen allergen, ChefDto chef) {
        feederService.removeAllergen(id, allergen, chef);

    }

    @Override
    public void likeRecipe(Long feedersId, Long recipesId, ChefDto chef) {
        feederService.likeRecipe(feedersId, recipesId, chef);
    }

    @Override
    public void dislikeRecipe(Long feedersId, Long recipesId, ChefDto chef) {
        feederService.dislikeRecipe(feedersId, recipesId, chef);
    }

    @Override
    public void addFoodIntolerance(Long feedersId, Long ingredientId, ChefDto chef) {
        feederService.addFoodIntolerance(feedersId, ingredientId, chef);
    }

    @Override
    public void removeFoodIntolerance(Long feedersId, Long ingredientId, ChefDto chef) {
        feederService.removeFoodIntolerance(feedersId, ingredientId, chef);
    }
}
