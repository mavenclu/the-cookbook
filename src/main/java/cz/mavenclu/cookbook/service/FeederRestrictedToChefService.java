package cz.mavenclu.cookbook.service;

import cz.mavenclu.cookbook.dao.FeederRepository;
import cz.mavenclu.cookbook.dao.RecipeRepository;
import cz.mavenclu.cookbook.dto.FeederDto;
import cz.mavenclu.cookbook.dto.FeederResponseDto;
import cz.mavenclu.cookbook.dto.FilterDto;
import cz.mavenclu.cookbook.entity.Allergen;
import cz.mavenclu.cookbook.entity.Feeder;
import cz.mavenclu.cookbook.entity.Ingredient;
import cz.mavenclu.cookbook.entity.Recipe;
import cz.mavenclu.cookbook.exception.FeederNotFoundException;
import cz.mavenclu.cookbook.mapper.FeederMapper;
import cz.mavenclu.cookbook.util.HelperClass;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.internal.util.collections.UniqueList;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;


import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class FeederRestrictedToChefService {

    private final FeederRepository feederRepo;
    private final FeederMapper feederMapper;
    private final RecipeRepository recipeRepo;
    private final IngredientService ingredientService;

    private HelperClass helperClass = new HelperClass();

    public FeederRestrictedToChefService(FeederRepository feederRepo, FeederMapper feederMapper, RecipeRepository recipeRepo, IngredientService ingredientService) {
        this.feederRepo = feederRepo;
        this.feederMapper = feederMapper;
        this.recipeRepo = recipeRepo;
        this.ingredientService = ingredientService;
    }

    public FeederResponseDto addNewFeeder(@Valid FeederDto feederDto) {
        log.info("addNewFeeder - adding with param: {}", feederDto);
        Feeder feeder = feederMapper.mapToFeeder(feederDto);
        log.info("addNewFeeder - created feeder with name: {}, chefId: {}", feeder.getName(), feeder.getChefId());
        feederRepo.save(feeder);
        log.info("addNewFeeder - saved newly created feeder with ID: {}. mapping to response dto", feeder.getId());
        FeederResponseDto responseFeeder = feederMapper.mapToFeederResponseDto(feeder);
        log.info("addNewFeeder - returned: {}", responseFeeder);
        return responseFeeder;

    }
    public FeederResponseDto updateFeedersName(Long id, FeederDto feederDto, Jwt chefPrincipal) {
        log.info("addNewFeeder - updating feeder with ID: {} and updates: {}", id, feederDto);
        Feeder feeder = findById(id, chefPrincipal);
        feederMapper.updateFromFeederDto(feederDto, feeder);
        feederRepo.save(feeder);
        log.info("addNewFeeder - updated feeder: {}", feeder);
        FeederResponseDto responseFeeder = feederMapper.mapToFeederResponseDto(feeder);
        log.info("addNewFeeder - got: {}", responseFeeder);
        return responseFeeder;
    }


    private Feeder findById(Long id, Jwt idToken){
        log.info("findById - looking for Feeder with ID: {}", id);
        Feeder feeder = feederRepo.findByIdAndChefId(id, helperClass.getPrincipalSub(idToken) ).orElseThrow(() -> new FeederNotFoundException(id));
        log.info("findById - found feeder with ID: {}", id);
        return feeder;
    }

    public List<FeederResponseDto> retrieveAll(Jwt idToken) {

        return feederMapper.mapToFeederResponseDtoList(feederRepo.findAllByChefId(helperClass.getPrincipalSub(idToken)));
    }

    public List<Feeder> findAllByChefId(Jwt idToken) {

        return feederRepo.findAllByChefId(helperClass.getPrincipalSub(idToken));
    }

    public FeederResponseDto retrieveFeeder(Long id, Jwt idToken) {
        log.info("findFeeder - looking for Feeder with ID: {}", id);
        FeederResponseDto responseDto = feederMapper.mapToFeederResponseDto(findById(id, idToken));
        log.info("findFeeder - mapped to: {}", responseDto);
        return responseDto;

    }

    public void deleteFeeder(Long id, Jwt idToken) {
        log.info("{} - deleteFeeder - checking if user has feeder with requested id: {}", this.getClass().getSimpleName(), id);
        if (hasChefFeederWithId(id, idToken)){
            feederRepo.deleteById(id);
            log.info("{} - deleteFeeder - deleted with ID: {}", this.getClass().getSimpleName(), id);
        }
    }

    private boolean hasChefFeederWithId(Long id, Jwt idToken){
        log.info("{} - hasChefFeederWithId - checking if chef: {} has feeder with id: {}", this.getClass().getSimpleName(), helperClass.getPrincipalSub(idToken), id);
        if (feederRepo.findById(id).isPresent()){
            boolean result = findAllByChefId(idToken)
                    .stream()
                    .anyMatch(feeder -> feeder.getId().equals(id));
            log.info("{} - hasChefFeederWithId  - {}", this.getClass().getSimpleName(), result);
            return result;
        }
        log.info("{} - hasChefFeederWithId - feeder with ID: {} not found in db", this.getClass().getSimpleName(), id);
        return false;
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
        Recipe recipe = recipeRepo.findById(recipesId).orElseThrow();
        feeder.likeRecipe(recipe);
        feederRepo.save(feeder);
        log.info("likeRecipe - marked recipe with ID: {} as liked by feeder with ID: {}", recipesId, feedersId);
    }

    public void dislikeRecipe(Long feedersId, Long recipesId, Jwt token) {
        log.info("dislikeRecipe - params feeder's ID: {} and recipe's ID: {}", feedersId, recipesId);
        Feeder feeder = findById(feedersId, token);
        Recipe recipe = recipeRepo.findById(recipesId).orElseThrow();
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


    public List<Feeder> getFeeders(FilterDto filterDto, Jwt token) {
        log.info("{} - getFeeders - getting Feeders with ID list param: {} ", this.getClass().getSimpleName(), filterDto.getConsumersIdList());
        List<Feeder> resultList = new ArrayList<>();
        List<String> feedersIds = filterDto.getConsumersIdList();
        log.info("{} - getFeeders -  list param has size: {} ", this.getClass().getSimpleName(), filterDto.getConsumersIdList().size());

        for (String id : feedersIds  ) {
            if (feederRepo.findByIdAndChefId(Long.parseLong(id), helperClass.getPrincipalSub(token)).isPresent()){
                log.info("{} - getFeeders - checking if Feeder with provided ID: {} is a feeder of the logged in user: {}", this.getClass().getSimpleName(), id, helperClass.getPrincipalSub(token));
                resultList.add(feederRepo.findByIdAndChefId(Long.parseLong(id), helperClass.getPrincipalSub(token)).get());
            }
        }
        log.info("{} - getFeeders - found {} feeders", this.getClass().getSimpleName(), resultList.size());
        return resultList;
    }

    public List<Allergen> getAllergensFromFeederList(List<Feeder> feederList) {
        log.info("{} - getAllergensFromFeederList - param: {} ", this.getClass().getSimpleName(), feederList);
        List<Allergen> result = new UniqueList<>();
        feederList.forEach(
                feeder -> result.addAll(feeder.getAllergens())
        );
        log.info("{} - getAllergensFromFeederList - got {} unique allergens - {}", this.getClass().getSimpleName(), result.size(), result);
        return result;
    }
}
