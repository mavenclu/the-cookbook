package cz.mavenclu.cookbook.entity;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Data
@Entity
public class RecipeItem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recipe_item_seq_gen")
    @SequenceGenerator(name = "recipe_item_seq_gen", sequenceName = "recipe_item_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    private Recipe recipe;

    private double amount;

    private Measure measure;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Ingredient ingredient;

    public enum Measure {
        CUP("cup"),
        G("g"),
        TO_TASTE("to taste"),
        TBSP("Tbsp"),
        TSP("tsp"),
        SLICE("slice"),
        HALF("half"),
        WHOLE("whole"),
        ML("ml");

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
