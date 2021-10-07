package cz.mavenclu.cookbook.service;

import cz.mavenclu.cookbook.dao.FeederRepository;
import cz.mavenclu.cookbook.dto.ChefDto;
import cz.mavenclu.cookbook.dto.FeederDto;
import cz.mavenclu.cookbook.dto.FeederResponseDto;
import cz.mavenclu.cookbook.entity.Allergen;
import cz.mavenclu.cookbook.entity.Feeder;
import cz.mavenclu.cookbook.entity.Ingredient;
import cz.mavenclu.cookbook.entity.Recipe;
import cz.mavenclu.cookbook.exception.FeederNotFoundException;
import cz.mavenclu.cookbook.mapper.FeederMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;


import java.util.List;

@Slf4j
@Service
public class FeederService {

    private final FeederRepository feederRepo;
    private final FeederMapper feederMapper;
    private final RecipeService recipeService;
    private final IngredientService ingredientService;

    public FeederService(FeederRepository feederRepo, FeederMapper feederMapper, RecipeService recipeService, IngredientService ingredientService) {
        this.feederRepo = feederRepo;
        this.feederMapper = feederMapper;
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
    }

    public FeederResponseDto addNewFeeder(FeederDto feederDto, Jwt idToken) {
        log.info("addNewFeeder - adding with param: {}", feederDto);
        Feeder feeder = Feeder.builder()
                .name(feederDto.getName())
                .chefId(getPrincipalSub(idToken))
                .build();
        log.info("addNewFeeder - created feeder with name: {}, chefId: {}", feeder.getName(), feeder.getChefId());
        feederRepo.save(feeder);
        log.info("addNewFeeder - saved newly created feeder with ID: {}", feeder.getId());
        log.info("addNewFeeder - mapping to response feeder");
        FeederResponseDto responseFeeder = feederMapper.mapToFeederResponseDto(feeder);
        log.info("addNewFeeder - returned: {}", responseFeeder);
        return responseFeeder;

    }
    public FeederResponseDto updateFeedersName(Long id, FeederDto feederDto, Jwt chefPrincipal) {
        log.info("addNewFeeder - updating feeder with ID: {} and updates: {}", id, feederDto);
        log.info("addNewFeeder - calling findById()");
        Feeder feeder = findById(id, chefPrincipal);
        feeder.setName(feederDto.getName());
        feederRepo.save(feeder);
        log.info("addNewFeeder - updated feeder: {}", feeder);
        FeederResponseDto responseFeeder = feederMapper.mapToFeederResponseDto(feeder);
        log.info("addNewFeeder - got: {}", responseFeeder);
        return responseFeeder;
    }


    private Feeder findById(Long id, Jwt idToken){
        log.info("findById - looking for Feeder with ID: {}", id);
        Feeder feeder = feederRepo.findByIdAndChefId(id, getPrincipalSub(idToken) ).orElseThrow(() -> new FeederNotFoundException(id));
        log.info("findById - found feeder with ID: {}", id);
        return feeder;
    }

    public List<FeederResponseDto> findAll(Jwt idToken) {

        return feederMapper.mapToFeederResponseDtoList(feederRepo.findAllByChefId(getPrincipalSub(idToken)));
    }

    public FeederResponseDto findFeeder(Long id, Jwt idToken) {
        log.info("findFeeder - looking for Feeder with ID: {}", id);
        FeederResponseDto responseDto = feederMapper.mapToFeederResponseDto(findById(id, idToken));
        log.info("findFeeder - mapped to: {}", responseDto);
        return responseDto;

    }

    public void deleteFeeder(Long id, Jwt token) {
        log.info("deleteFeeder - deleting Feeder with ID: {}", id);
        feederRepo.delete(findById(id, token));
        log.info("deleteFeeder - feeder deleted");
    }

    public void addAllergen(Long id, Allergen allergen, Jwt token) {
        log.info("addAllergen - params feeder with ID: {} and allergen: {}", id, allergen);
        Feeder feeder = findById(id, token);
        feeder.addAllergen(allergen);
        feederRepo.save(feeder);
        log.info("addNewFeeder - added allergen to feeder with ID: {}", id);
    }

    public void removeAllergen(Long id, Allergen allergen, Jwt token) {
        log.info("removeAllergen - params feeder with ID: {} and allergen: {}", id, allergen);
        Feeder feeder = findById(id, token);
        feeder.removeAllergen(allergen);
        feederRepo.save(feeder);
        log.info("removeAllergen - removed allergen from feeder with ID: {}", id);
    }

    public void likeRecipe(Long feedersId, Long recipesId, Jwt token) {
        log.info("likeRecipe - params feeders ID: {} and recipe's ID: {}", feedersId, recipesId);
        Feeder feeder = findById(feedersId, token);
        Recipe recipe = recipeService.getById(recipesId);
        feeder.likeRecipe(recipe);
        feederRepo.save(feeder);
        log.info("likeRecipe - marked recipe with ID: {} as liked by feeder with ID: {}", recipesId, feedersId);
    }

    public void dislikeRecipe(Long feedersId, Long recipesId, Jwt token) {
        log.info("dislikeRecipe - params feeder's ID: {} and recipe's ID: {}", feedersId, recipesId);
        Feeder feeder = findById(feedersId, token);
        Recipe recipe = recipeService.getById(recipesId);
        feeder.dislikeRecipe(recipe);
        log.info("dislikeRecipe - removed recipe with ID: {} from favorites of feeder with ID: {}", feedersId, recipesId);
    }

    public void addFoodIntolerance(Long feedersId, Long ingredientId, Jwt jwt) {
        Feeder feeder = findById(feedersId, jwt);
        Ingredient ingredient = ingredientService.getById(ingredientId);
        feeder.addIntolerance(ingredient);
    }

    public void removeFoodIntolerance(Long feedersId, Long ingredientId, Jwt jwt) {
        Feeder feeder = findById(feedersId, jwt);
        Ingredient ingredient = ingredientService.getById(ingredientId);
        feeder.removeIntolerance(ingredient);
    }

    private String getPrincipalSub(Jwt idToken){
        return (idToken != null) ? idToken.getClaimAsString("sub").substring(6) : "";

    }
}
