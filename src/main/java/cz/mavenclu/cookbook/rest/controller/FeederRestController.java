package cz.mavenclu.cookbook.rest.controller;

import cz.mavenclu.cookbook.dto.FeederDto;
import cz.mavenclu.cookbook.dto.FeederResponseDto;
import cz.mavenclu.cookbook.entity.Allergen;
import cz.mavenclu.cookbook.service.FeederRestrictedToChefService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@Slf4j
@RestController
public class FeederRestController implements FeederApi{

    private final FeederRestrictedToChefService feederService;

    public FeederRestController(FeederRestrictedToChefService feederService) {
        this.feederService = feederService;
    }

    @Override
    public FeederResponseDto addNewFeeder(FeederDto feeder, Jwt idToken) {
        log.info("addNewFeeder() - adding feeder with param: {}", feeder);
        return feederService.addNewFeeder(feeder);

    }

    @Override
    public FeederResponseDto updateFeedersName(Long id, FeederDto feeder, Jwt idToken) {
        return feederService.updateFeedersName(id, feeder, idToken);
    }

    @Override
    public List<FeederResponseDto> getAllFeeders( Jwt idToken) {
        return feederService.retrieveAll(idToken);
    }

    @Override
    public FeederResponseDto getFeeder(Long id, Jwt idToken) {
        return feederService.retrieveFeeder(id, idToken);
    }

    @Override
    public void delete(Long id, Jwt idToken) {
        feederService.deleteFeeder(id, idToken);
    }

    @Override
    public void addAllergen(Long id, Allergen allergen, Jwt idToken) {
        feederService.addAllergen(id, allergen, idToken);
    }

    @Override
    public void removeAllergen(Long id, Allergen allergen, Jwt idToken) {
        feederService.removeAllergen(id, allergen, idToken);

    }

    @Override
    public void likeRecipe(Long feedersId, Long recipesId, Jwt idToken) {
        feederService.likeRecipe(feedersId, recipesId, idToken);
    }

    @Override
    public void dislikeRecipe(Long feedersId, Long recipesId, Jwt idToken) {
        feederService.dislikeRecipe(feedersId, recipesId, idToken);
    }

    @Override
    public void addFoodIntolerance(Long feedersId, Long ingredientId, Jwt idToken) {
        feederService.addFoodIntolerance(feedersId, ingredientId, idToken);
    }

    @Override
    public void removeFoodIntolerance(Long feedersId, Long ingredientId, Jwt idToken) {
        feederService.removeFoodIntolerance(feedersId, ingredientId, idToken);
    }
}
