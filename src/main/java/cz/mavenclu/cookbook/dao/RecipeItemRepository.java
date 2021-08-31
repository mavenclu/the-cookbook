package cz.mavenclu.cookbook.dao;

import cz.mavenclu.cookbook.entity.RecipeItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeItemRepository extends JpaRepository<RecipeItem, Long> {

    List<RecipeItem> findAllByRecipe_Id(Long recipeID);
}
