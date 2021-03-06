package cz.mavenclu.cookbook.dao;

import cz.mavenclu.cookbook.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    List<Ingredient> findIngredientByNameStartingWith(String searchTerm);
    List<Ingredient> findIngredientByNameContaining(String searchTerm);
    Optional<Ingredient> findByNameAndDescription(String name, String description);

}
