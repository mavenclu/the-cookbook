package cz.mavenclu.cookbook.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Feeder {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "feeder_seq_gen")
    @SequenceGenerator(name = "feeder_seq_gen", sequenceName = "feeder_id_seq", allocationSize = 1)
    private Long id;
    private String name;
    private String chefId;

    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = Allergen.class)
    private List<Allergen> allergens = new ArrayList<>();
    @ManyToMany
    private List<Ingredient> ingredientIntolerances = new ArrayList<>();

   @ManyToMany
   private List<Recipe> favoriteRecipes = new ArrayList<>();

    public void setAllergens(List<Allergen> allergens) {
        this.allergens = allergens;
    }

    public List<Allergen> getAllergens() {
        return allergens;
    }

    public void addAllergen(Allergen allergen) {
        this.allergens.add(allergen);
    }
    public void removeAllergen(Allergen allergen) {
        this.allergens.remove(allergen);
    }
    public void likeRecipe(Recipe recipe) {
        this.favoriteRecipes.add(recipe);
    }
    public void dislikeRecipe(Recipe recipe) {
        this.favoriteRecipes.remove(recipe);
    }
    public void addIntolerance(Ingredient ingredient){
        this.ingredientIntolerances.add(ingredient);
    }
    public void removeIntolerance(Ingredient ingredient){
        this.ingredientIntolerances.remove(ingredient);
    }

}
