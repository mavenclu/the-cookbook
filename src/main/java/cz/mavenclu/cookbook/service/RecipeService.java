package cz.mavenclu.cookbook.service;

import cz.mavenclu.cookbook.dao.IngredientRepository;
import cz.mavenclu.cookbook.dao.RecipeItemRepository;
import cz.mavenclu.cookbook.dao.RecipeRepository;
import cz.mavenclu.cookbook.dto.FilterDto;
import cz.mavenclu.cookbook.dto.RecipeDto;
import cz.mavenclu.cookbook.dto.RecipeResponseDto;
import cz.mavenclu.cookbook.entity.Feeder;
import cz.mavenclu.cookbook.entity.RecipeItem;
import cz.mavenclu.cookbook.exception.RecipeNotFoundException;
import cz.mavenclu.cookbook.mapper.RecipeItemMapper;
import cz.mavenclu.cookbook.mapper.RecipeMapper;
import cz.mavenclu.cookbook.entity.Recipe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RecipeService {

    private final RecipeRepository recipeRepo;
    private final RecipeItemRepository recipeItemRepo;
    private final IngredientRepository ingredientRepo;
    private final RecipeMapper recipeMapper;
    private final RecipeItemMapper recipeItemMapper;
    private final RecipeItemService recipeItemService;
    private final FeederService feederService;

    public RecipeService(RecipeRepository recipeRepo, RecipeItemRepository recipeItemRepo, IngredientRepository ingredientRepo, RecipeMapper recipeMapper, RecipeItemMapper recipeItemMapper, RecipeItemService recipeItemService, FeederService feederService) {
        this.recipeRepo = recipeRepo;
        this.recipeItemRepo = recipeItemRepo;
        this.ingredientRepo = ingredientRepo;
        this.recipeMapper = recipeMapper;
        this.recipeItemMapper = recipeItemMapper;
        this.recipeItemService = recipeItemService;
        this.feederService = feederService;
    }

    public Recipe addRecipe(RecipeDto recipeDto){
        log.info("addRecipe() - add recipe with params: {}", recipeDto);
        log.info("addRecipe() - calling recipeMapper - mapToRecipe()");
        Recipe recipe = recipeMapper.mapToRecipe(recipeDto);
        log.info("addRecipe() - mapped to: {}", recipe);
        log.info("addRecipe() - calling save() on recipe");
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

    public Recipe updateRecipe(RecipeDto recipeDto, Recipe recipe){
        log.info("updateRecipe() - update recipe: {} to : {}", recipe, recipeDto);
        log.info("updateRecipe() - deleting old recipe items");
        recipeItemService.deleteRecipeItems(recipe.getId());
        log.info("updateRecipe() - calling recipeMapper - updateFromRecipeDto()");
        Recipe updatedRecipe = recipeMapper.updateFromRecipeDto(recipeDto, recipe);
        log.info("updateRecipe() - mapped to: {}", updatedRecipe);
        log.info("updateRecipe() - calling save() on recipe");
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

    public RecipeResponseDto getRecipe(Long id){
        log.info("getRecipe() - get RecipeResponseDto for recipe with ID: {}", id);
        log.info("getRecipe() - calling recipeMapper - mapToRecipeResponseDto()");
        RecipeResponseDto responseDto = recipeMapper.mapToRecipeResponseDto(getById(id));
        log.info("getRecipe() - mapped to recipeResponseDto: {}", responseDto);
        return responseDto;
    }

    public Recipe getById(Long id){
        log.info("getById() - find recipe with ID: {}", id);
        Recipe recipe =  recipeRepo.findById(id).orElseThrow(() -> new RecipeNotFoundException(id));
        log.info("getById() - found recipe with ID: {}", id);
        return recipe;
    }

    public List<RecipeResponseDto> getAllRecipes(){
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
        return responseDtos;
    }

    public List<RecipeResponseDto> filterRecipes(FilterDto filterDto, Jwt token) {
        List<Recipe> result = new ArrayList<>();
        String chefId = feederService.getPrincipalSub(token);
        List<Feeder> feeders = feederService.findAllEnitites(token);

        if (filterDto.getCuisine() != null && filterDto.getDiet() != null
                && filterDto.getDifficulty() != null && filterDto.getRequiredTime() != null
                && filterDto.getConsumers() != null){
            result = filterByDietAndCuisineAndDifficultyAndRequiredTimeInterval(filterDto);

        } else if (filterDto.getCuisine() != null && filterDto.getDiet() != null
                && filterDto.getDifficulty() != null && filterDto.getRequiredTime() != null){
            result = filterByDietAndCuisineAndDifficultyAndRequiredTimeInterval(filterDto);
            log.info("FILTER REICPES - found: {}", result);
        }  else {
                result = recipeRepo.findAll();
        }


        log.info("{} - filterRecipes - found {} recipes", this.getClass().getSimpleName(), result.size());
        return recipeMapper.mapToRecipeResponseDtoList(result);
    }

    public List<Recipe> filterByDietAndCuisineAndDifficultyAndRequiredTimeInterval(FilterDto filterDto){
        log.info("filtering by Diet Cuisine Difficulty - with param: {}", filterDto);
        List<Recipe> result;
        List<Recipe> recipesByDietCuisineDifficulty = recipeRepo.findAllByCuisineAndDietsInAndDifficulty(filterDto.getCuisine(), List.of(filterDto.getDiet()), filterDto.getDifficulty());
        log.info("filterByDietAndCuisineAndDifficultyAndRequiredTimeInterval - by Diet Cuisine Difficulty - found {} recipes", recipesByDietCuisineDifficulty.size());
        List<Recipe> recipesByTimeInterval = getRecipesByRequiredTime(filterDto.getRequiredTime());
        log.info("filterByDietAndCuisineAndDifficultyAndRequiredTimeInterval - by requiring time - found: {} ", recipesByTimeInterval.size());
        result = recipesByDietCuisineDifficulty.stream()
                .filter(recipesByTimeInterval::contains)
                .collect(Collectors.toList());
        return  result;
    }


    public List<Recipe> getRecipesByRequiredTime(Recipe.RequiredTimeInterval timeInterval){
        List<Recipe> result;
        switch (timeInterval){
            case FAST:
                result = recipeRepo.findAllFastRecipes();
                break;
            case MEDIUM:
                result = recipeRepo.findAllMediumRecipes();
                break;
            case SLOW:
                result = recipeRepo.findAllSlowRecipes();
                break;
            default:
                result = new ArrayList<>();
        }

        return result;
    }
}
