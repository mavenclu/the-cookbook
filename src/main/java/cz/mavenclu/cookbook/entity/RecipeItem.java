package cz.mavenclu.cookbook.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class RecipeItem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recipe_item_seq_gen")
    @SequenceGenerator(name = "recipe_item_seq_gen", sequenceName = "recipe_item_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;


    private String amount;

    private Measure measure;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Ingredient ingredient;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public String toString() {
        return "RecipeItem{" +
                "id=" + id +
                ", amount='" + amount + '\'' +
                ", measure=" + measure +
                ", ingredient=" + ingredient +
                ", recipe=" + recipe +
                '}';
    }

    public enum Measure {
        CUP("cup"),
        G("g"),
        TO_TASTE("to taste"),
        TBSP("Tbsp"),
        TSP("tsp"),
        SLICE("slice"),
        HALF("half"),
        WHOLE("whole"),
        ML("ml"),
        NON("");


        private final String label;
        private Measure(String value){
            this.label = value;
        }

        public String getLabel() {
            return label;
        }

        @Override
        public String toString() {
            return label;
        }
    }
}
