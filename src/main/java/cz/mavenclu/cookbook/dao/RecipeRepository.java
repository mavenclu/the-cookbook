package cz.mavenclu.cookbook.dao;

import cz.mavenclu.cookbook.entity.Recipe;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository  extends JpaRepository<Recipe, Long> {

    List<Recipe> findAllByCuisine(Recipe.Cuisine cuisine, Sort sort);

    List<Recipe> findAllByCuisineAndDietsInAndDifficulty(Recipe.Cuisine cuisine, List<Recipe.Diet> diets, Recipe.Difficulty difficulty);

    @Query("select u from #{#entityName} u where u.prepTime + u.cookingTime <= 30")
    List<Recipe> findAllFastRecipes();

    @Query("select u from #{#entityName} u " +
            "where u.prepTime + u.cookingTime >= 30 and u.prepTime + u.cookingTime <= 60")
    List<Recipe> findAllMediumRecipes();

    @Query("select u from #{#entityName} u where u.prepTime + u.cookingTime > 60")
    List<Recipe> findAllSlowRecipes();



}
