package cz.mavenclu.cookbook.service;

import cz.mavenclu.cookbook.dao.RecipeItemRepository;
import cz.mavenclu.cookbook.dao.RecipeRepository;
import cz.mavenclu.cookbook.dto.FilterDto;
import cz.mavenclu.cookbook.dto.RecipeDto;
import cz.mavenclu.cookbook.dto.RecipeResponseDto;
import cz.mavenclu.cookbook.entity.Allergen;
import cz.mavenclu.cookbook.entity.Feeder;
import cz.mavenclu.cookbook.entity.Ingredient;
import cz.mavenclu.cookbook.entity.RecipeItem;
import cz.mavenclu.cookbook.exception.RecipeNotFoundException;
import cz.mavenclu.cookbook.mapper.RecipeItemMapper;
import cz.mavenclu.cookbook.mapper.RecipeMapper;
import cz.mavenclu.cookbook.entity.Recipe;
import cz.mavenclu.cookbook.util.HelperClass;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.internal.util.collections.UniqueList;
import org.springframework.data.domain.Sort;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RecipeService {

    private final RecipeRepository recipeRepo;
    private final RecipeItemRepository recipeItemRepo;
    private final RecipeMapper recipeMapper;
    private final RecipeItemMapper recipeItemMapper;
    private final RecipeItemService recipeItemService;
    private final FeederRestrictedToChefService feederService;

    public RecipeService(RecipeRepository recipeRepo, RecipeItemRepository recipeItemRepo, RecipeMapper recipeMapper, RecipeItemMapper recipeItemMapper, RecipeItemService recipeItemService, FeederRestrictedToChefService feederService) {
        this.recipeRepo = recipeRepo;
        this.recipeItemRepo = recipeItemRepo;
        this.recipeMapper = recipeMapper;
        this.recipeItemMapper = recipeItemMapper;
        this.recipeItemService = recipeItemService;
        this.feederService = feederService;
    }

    public Recipe addRecipe(RecipeDto recipeDto) {
        log.info("addRecipe() - add recipe with params: {}", recipeDto);
        log.info("addRecipe() - calling recipeMapper - mapToRecipe()");
        Recipe recipe = recipeMapper.mapToRecipe(recipeDto);
        log.info("addRecipe() - mapped to: {}", recipe);
        log.info("addRecipe() - calling save() on recipe");
        recipe.setTotalCookingTime(recipeDto.getPrepTime() + recipeDto.getCookingTime());
        recipeRepo.save(recipe);
        log.info("addRecipe() - saved recipe with ID: {}", recipe.getId());
        log.info("addRecipe() - calling recipeMapper - mapToRecipeItemList with recipeDTO: {} and recipe: {}", recipeDto, recipe);
        List<RecipeItem> items = recipeItemMapper.mapToRecipeItemList(recipeDto, recipe);
        log.info("addRecipe() - recipe items mapped to: {}. Calling save() on items", items);
        recipeItemRepo.saveAll(items);
        log.info("addRecipe() - saved items");
        log.info("addRecipe() - added recipe with ID: {} and ingredients: {}", recipe.getId(), items);
        return recipe;
    }

    public Recipe updateRecipe(RecipeDto recipeDto, Recipe recipe) {
        log.info("updateRecipe() - update recipe: {} to : {}", recipe, recipeDto);
        log.info("updateRecipe() - deleting old recipe items");
        recipeItemService.deleteRecipeItems(recipe.getId());
        log.info("updateRecipe() - calling recipeMapper - updateFromRecipeDto()");
        Recipe updatedRecipe = recipeMapper.updateFromRecipeDto(recipeDto, recipe);
        log.info("updateRecipe() - mapped to: {}", updatedRecipe);
        log.info("updateRecipe() - calling save() on recipe");
        recipe.setTotalCookingTime(recipeDto.getPrepTime() + recipeDto.getCookingTime());
        recipeRepo.save(recipe);
        log.info("updateRecipe() - saved recipe with ID: {}", updatedRecipe.getId());
        log.info("updateRecipe() - calling recipeMapper - mapToRecipeItemList with recipeDTO: {} and recipe: {}", recipeDto, updatedRecipe);
        List<RecipeItem> items = recipeItemMapper.mapToRecipeItemList(recipeDto, updatedRecipe);
        log.info("updateRecipe() - recipe items mapped to: {}. Calling save() on items", items);
        recipeItemRepo.saveAll(items);
        log.info("updateRecipe() - saved items");
        log.info("updateRecipe() - updated recipe with ID: {} and ingredients: {}", updatedRecipe.getId(), items);
        return updatedRecipe;
    }

    public RecipeResponseDto getRecipe(Long id) {
        log.info("getRecipe() - get RecipeResponseDto for recipe with ID: {}", id);
        log.info("getRecipe() - calling recipeMapper - mapToRecipeResponseDto()");
        RecipeResponseDto responseDto = recipeMapper.mapToRecipeResponseDto(getById(id));
        log.info("getRecipe() - mapped to recipeResponseDto: {}", responseDto);
        return responseDto;
    }

    public Recipe getById(Long id) {
        log.info("getById() - find recipe with ID: {}", id);
        Recipe recipe = recipeRepo.findById(id).orElseThrow(() -> new RecipeNotFoundException(id));
        log.info("getById() - found recipe with ID: {}", id);
        return recipe;
    }

    public List<RecipeResponseDto> getAllRecipes() {
        List<Recipe> recipes = recipeRepo.findAll();
        log.info("getAll() - get all recipes response dtos");
        log.info("getAll() - found {} recipes: ", recipes.size());
        log.info("getAll() - calling recipeMapper - mapToRecipeResponseDtoList()");
        List<RecipeResponseDto> responseDtos = recipeMapper.mapToRecipeResponseDtoList(
                recipeRepo.findAll(Sort.by(Sort.Direction.DESC, "lastModifiedDate")));
        log.info("getAll() - mapped to : {}", responseDtos);
        return responseDtos;
    }


    public List<RecipeResponseDto> getAllRecipesByCuisine(Recipe.Cuisine cuisine) {

        List<Recipe> recipes = recipeRepo.findAllByCuisine(cuisine, Sort.by(Sort.Direction.DESC, "lastModifiedDate"));
        List<RecipeResponseDto> responseDtos = recipeMapper.mapToRecipeResponseDtoList(recipes);
        log.info("{} - getAllRecipesByCuisine - found {} recipes", this.getClass().getSimpleName(), responseDtos.size());
        return responseDtos;
    }

    public List<Allergen> getAllergens(Recipe recipe){
        return recipe.getRecipeItems().stream()
                .map(RecipeItem::getIngredient)
                .filter(Ingredient::isAllergen)
                .map(Ingredient::getAllergen)
                .distinct()
                .collect(Collectors.toList());
    }


    public List<RecipeResponseDto> filterRecipes(FilterDto filterDto, Jwt token) {
        log.info("{} - filterRecipes - with param: {} and token hash: {}", this.getClass().getSimpleName(), filterDto, token.getTokenValue().hashCode());


        List<Feeder> feeders = feederService.getFeeders(filterDto, token);
        List<Allergen> feedersAllergens = feederService.getAllergensFromFeederList(feeders);
        List<Recipe> filteredResult;
        List<Recipe> tempFilter;
        int start = getRequiredTimeInterval(filterDto)[0];
        int end = getRequiredTimeInterval(filterDto)[1];


        tempFilter = recipeRepo.findAllByCuisineAndDietAndDifficultyAndTotalCookingTimeBetween(
                filterDto.getCuisine(),
                filterDto.getDiet(),
                filterDto.getDifficulty(),
                start,
                end);
        log.info("{} - filterRecipes - got partial filtered result from repo with size: {}", this.getClass().getSimpleName(), tempFilter.size());

        filteredResult = tempFilter.stream()
                        .filter(recipe -> {
                                    log.info("filterRecipe - filtering based on feeders' allergens - " +
                                            "got recipe with ID: {},  and result: {}", recipe.getId(),  isRecipeSafeByAllergensListPred.test(recipe, feedersAllergens));
                                    return isRecipeSafeByAllergensListPred.test(recipe, feedersAllergens);
                                })
                        .collect(Collectors.toList());

        log.info("filterRecipes - found {} recipes", filteredResult.size());

        return recipeMapper.mapToRecipeResponseDtoList(filteredResult);
    }




    private int[] getRequiredTimeInterval(FilterDto filterDto){
        int start;
        int end;

        if (filterDto.getRequiredTime() != null) {
            start = filterDto.getRequiredTime().getStart();
            end = filterDto.getRequiredTime().getEnd();
        } else {
            start = 0;
            end = Integer.MAX_VALUE;
        }

        return new int[]{start, end};
    }

    /**
     * check if provided
      * @param recipe contains
     * @param allergen
     * @return true or false
     */
   public boolean isRecipeSafeByAllergen(Recipe recipe, Allergen allergen){
        return ! getAllergens(recipe).contains(allergen);
   }

    BiPredicate<Recipe, Allergen> isRecipeSafeByAllergenPred = (recipe, allergen) -> ! getAllergens(recipe).contains(allergen);

    /**
     * check if
     * @param recipe provided contains any of the allergens provided in
     * @param allergens list
     * @return true if non of allergens provided in the list of allergens is contained in recipe allergens
     */
   public boolean isRecipeSafeByAllergenList(Recipe recipe, List<Allergen> allergens){
       return getAllergens(recipe).stream()
               .noneMatch(allergens::contains);
   }

    BiPredicate<Recipe, List<Allergen>> isRecipeSafeByAllergensListPred =
            (recipe, allergens) -> {
                log.info("isRecipeSafeByAllergensListPred - checking for recipe with ID: {} with recipe allergens: {} against allergen: {} - got result: {}",
                        recipe.getId(), getAllergens(recipe), allergens, getAllergens(recipe).stream().noneMatch(allergens::contains));

                return getAllergens(recipe).stream().noneMatch(allergens::contains);
            };



}
