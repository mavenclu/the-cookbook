package cz.mavenclu.cookbook.dao;

import cz.mavenclu.cookbook.entity.Recipe;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository  extends JpaRepository<Recipe, Long> {

    List<Recipe> findAllByCuisine(Recipe.Cuisine cuisine, Sort sort);

}
