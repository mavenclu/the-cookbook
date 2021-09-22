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
import org.springframework.stereotype.Service;

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

    public FeederResponseDto addNewFeeder(FeederDto feederDto, ChefDto chef) {
        log.info("addNewFeeder - adding with param: {}", feederDto);
        Feeder feeder = Feeder.builder()
                .name(feederDto.getName())
                .chefId(chef.getId())
                .build();
        log.info("addNewFeeder - created feeder with name: {}, chefId: {}", feeder.getName(), feeder.getChefId());
        feederRepo.save(feeder);
        log.info("addNewFeeder - saved newly created feeder with ID: {}", feeder.getId());
        log.info("addNewFeeder - mapping to response feeder");
        FeederResponseDto responseFeeder = feederMapper.mapToFeederResponseDto(feeder);
        log.info("addNewFeeder - returned: {}", responseFeeder);
        return responseFeeder;

    }
    public FeederResponseDto updateFeedersName(Long id, FeederDto feederDto, ChefDto chef) {
        log.info("addNewFeeder - updating feeder with ID: {} and updates: {}", id, feederDto);
        log.info("addNewFeeder - calling findById()");
        Feeder feeder = findById(id, chef);
        feeder.setName(feederDto.getName());
        feederRepo.save(feeder);
        log.info("addNewFeeder - updated feeder: {}", feeder);
        FeederResponseDto responseFeeder = feederMapper.mapToFeederResponseDto(feeder);
        log.info("addNewFeeder - got: {}", responseFeeder);
        return responseFeeder;
    }


    private Feeder findById(Long id, ChefDto chef){
        log.info("findById - looking for Feeder with ID: {}", id);
        Feeder feeder = feederRepo.findByIdAndChefId(id, chef.getId() ).orElseThrow(() -> new FeederNotFoundException(id));
        log.info("findById - found feeder with ID: {}", id);
        return feeder;
    }

    public List<FeederResponseDto> findAll(ChefDto chef) {
        return feederMapper.mapToFeederResponseDtoList(feederRepo.findAllByChefId(chef.getId()));
    }

    public FeederResponseDto findFeeder(Long id, ChefDto chef) {
        log.info("findFeeder - looking for Feeder with ID: {}", id);
        FeederResponseDto responseDto = feederMapper.mapToFeederResponseDto(findById(id, chef));
        log.info("findFeeder - mapped to: {}", responseDto);
        return responseDto;

    }

    public void deleteFeeder(Long id, ChefDto chef) {
        log.info("deleteFeeder - deleting Feeder with ID: {}", id);
        feederRepo.delete(findById(id, chef));
        log.info("deleteFeeder - feeder deleted");
    }

    public void addAllergen(Long id, Allergen allergen, ChefDto chef) {
        log.info("addAllergen - params feeder with ID: {} and allergen: {}", id, allergen);
        Feeder feeder = findById(id, chef);
        feeder.addAllergen(allergen);
        feederRepo.save(feeder);
        log.info("addNewFeeder - added allergen to feeder with ID: {}", id);
    }

    public void removeAllergen(Long id, Allergen allergen, ChefDto chef) {
        log.info("removeAllergen - params feeder with ID: {} and allergen: {}", id, allergen);
        Feeder feeder = findById(id, chef);
        feeder.removeAllergen(allergen);
        feederRepo.save(feeder);
        log.info("removeAllergen - removed allergen from feeder with ID: {}", id);
    }

    public void likeRecipe(Long feedersId, Long recipesId, ChefDto chef) {
        log.info("likeRecipe - params feeders ID: {} and recipe's ID: {}", feedersId, recipesId);
        Feeder feeder = findById(feedersId, chef);
        Recipe recipe = recipeService.getById(recipesId);
        feeder.likeRecipe(recipe);
        feederRepo.save(feeder);
        log.info("likeRecipe - marked recipe with ID: {} as liked by feeder with ID: {}", recipesId, feedersId);
    }

    public void dislikeRecipe(Long feedersId, Long recipesId, ChefDto chef) {
        log.info("dislikeRecipe - params feeder's ID: {} and recipe's ID: {}", feedersId, recipesId);
        Feeder feeder = findById(feedersId, chef);
        Recipe recipe = recipeService.getById(recipesId);
        feeder.dislikeRecipe(recipe);
        log.info("dislikeRecipe - removed recipe with ID: {} from favorites of feeder with ID: {}", feedersId, recipesId);
    }

    public void addFoodIntolerance(Long feedersId, Long ingredientId, ChefDto chef) {
        Feeder feeder = findById(feedersId, chef);
        Ingredient ingredient = ingredientService.getById(ingredientId);
        feeder.addIntolerance(ingredient);
    }

    public void removeFoodIntolerance(Long feedersId, Long ingredientId, ChefDto chef) {
        Feeder feeder = findById(feedersId, chef);
        Ingredient ingredient = ingredientService.getById(ingredientId);
        feeder.removeIntolerance(ingredient);
    }
}
