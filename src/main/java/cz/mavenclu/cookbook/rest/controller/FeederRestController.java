package cz.mavenclu.cookbook.rest.controller;

import cz.mavenclu.cookbook.dto.FeederDto;
import cz.mavenclu.cookbook.dto.FeederResponseDto;
import cz.mavenclu.cookbook.entity.Allergen;
import cz.mavenclu.cookbook.service.FeederService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
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
    public FeederResponseDto addNewFeeder(FeederDto feeder, Jwt idToken) {
        log.info("addNewFeeder() - adding feeder with param: {}", feeder);
        return feederService.addNewFeeder(feeder, idToken);

    }

    @Override
    public FeederResponseDto updateFeedersName(Long id, FeederDto feeder, Jwt idToken) {
        return feederService.updateFeedersName(id, feeder, idToken);
    }

    @Override
    public List<FeederResponseDto> getAllFeeders( Jwt idToken) {
        log.info("{} - findAll - param token: {}", this.getClass().getSimpleName(), idToken);
        log.info("{} - findAll - token claims: {}", this.getClass().getSimpleName(), idToken.getClaims());
        log.info("{} - findAll - token sub claim: {}", this.getClass().getSimpleName(), idToken.getClaims().get("sub"));

        return feederService.findAll(idToken);
    }

    @Override
    public FeederResponseDto getFeeder(Long id, Jwt idToken) {
        return feederService.findFeeder(id, idToken);
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
