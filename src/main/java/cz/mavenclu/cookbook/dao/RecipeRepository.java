package cz.mavenclu.cookbook.dao;

import cz.mavenclu.cookbook.entity.Allergen;
import cz.mavenclu.cookbook.entity.Recipe;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository  extends JpaRepository<Recipe, Long> {

    List<Recipe> findAllByCuisine(Recipe.Cuisine cuisine, Sort sort);

////todo jak overit ze recept neobsahuje alergeny zadane param
//    @Query("select r from #{#entityName} r where (:cuisine is null or r.cuisine = :cuisine) " +
//            "and (:difficulty is null or r.difficulty = :difficulty) " +
//            "and (:diet is null or r.diet = :diet) " +
//            "and (:start is null or (r.totalCookingTime >= :start and r.totalCookingTime <= :end)) " +
//            "and (:allergens is null or r.allergens not in :allergens)")
//    List<Recipe> findAllByCuisineAndDietAndDifficultyAndTotalCookingTimeBetweenAndAllergensNotContaining(
//            @Param("cuisine")Recipe.Cuisine  cuisine,
//            @Param("diet") Recipe.Diet diet,
//            @Param("difficulty") Recipe.Difficulty difficulty,
//            @Param("start") int startVal,
//            @Param("end") int endVal,
//            @Param("allergens")List<Allergen> allergens);

    @Query("select r from #{#entityName} r where (:cuisine is null or r.cuisine = :cuisine) " +
            "and (:difficulty is null or r.difficulty = :difficulty) " +
            "and (:diet is null or r.diet = :diet) " +
            "and (:start is null or (r.totalCookingTime >= :start and r.totalCookingTime <= :end))")
    List<Recipe> findAllByCuisineAndDietAndDifficultyAndTotalCookingTimeBetween(
            @Param("cuisine")Recipe.Cuisine  cuisine,
            @Param("diet") Recipe.Diet diet,
            @Param("difficulty") Recipe.Difficulty difficulty,
            @Param("start") int startVal,
            @Param("end") int endVal);



    @Query("select u from #{#entityName} u where u.prepTime + u.cookingTime <= 30")
    List<Recipe> findAllFastRecipes();

    @Query("select u from #{#entityName} u " +
            "where u.prepTime + u.cookingTime >= 30 and u.prepTime + u.cookingTime <= 60")
    List<Recipe> findAllMediumRecipes();

    @Query("select u from #{#entityName} u where u.prepTime + u.cookingTime > 60")
    List<Recipe> findAllSlowRecipes();



}
