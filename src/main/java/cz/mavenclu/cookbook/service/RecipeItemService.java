package cz.mavenclu.cookbook.service;

import cz.mavenclu.cookbook.dao.RecipeItemRepository;
import cz.mavenclu.cookbook.entity.Recipe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class RecipeItemService {

    private final RecipeItemRepository recipeItemRepo;

    public RecipeItemService(RecipeItemRepository recipeItemRepo) {
        this.recipeItemRepo = recipeItemRepo;
    }

    public void deleteRecipeItems(Long recipeId){
        recipeItemRepo.deleteAll(
                recipeItemRepo.findAllByRecipe_Id(recipeId)
        );

    }
}
