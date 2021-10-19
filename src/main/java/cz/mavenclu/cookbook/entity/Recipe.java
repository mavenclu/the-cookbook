package cz.mavenclu.cookbook.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Recipe extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recipe_seq_gen")
    @SequenceGenerator(name = "recipe_seq_gen", sequenceName = "recipe_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    private String title;
    private String description;
    @ElementCollection
    @JoinTable(name = "recipe_instruction")
    @Column(name = "instruction")
    private List<String> instructions;
    private int prepTime;
    private int cookingTime;
    @Column
    private int yields;

    @Enumerated(EnumType.STRING)
    private Diet diet;

    @Enumerated(EnumType.STRING)
    private Cuisine cuisine;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @OneToMany(mappedBy = "recipe")
    private List<RecipeItem> recipeItems;
  //todo zeptat se - nepotrebuji u receptu jeste ukladat alergeny, kdyz do alergenu patri jednotlive ingredience
    @Getter(AccessLevel.NONE)
    @Transient
    private List<Allergen> allergens;

    private int totalCookingTime;


    public List<Allergen> getAllergens(){
        return this.getRecipeItems().stream()
                .map(RecipeItem::getIngredient)
                .filter(Ingredient::isAllergen)
                .map(Ingredient::getAllergen)
                .distinct()
                .collect(Collectors.toList());
    }


    public enum Cuisine {
        CZECH("Czech"),
        ASIAN("Asian"),
        MEDITERRANEAN("Mediterranean"),
        AMERICAN("American"),
        THAI("Thai"),
        INDIAN("Indian"),
        OTHER("Other");

        private final String label;

        private Cuisine(String value){
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

    public enum Diet {
        VEGAN("Vegan"),
        OMNIVORE("Omnivore"),
        VEGETARIAN("Vegetarian"),
        GLUTEN_FREE("Gluten free"),
        DIARY_FREE("Diary free"),
        EGG_FREE("No eggs"),
        REFINE_SUGARS_FREE("Refine sugars free"),
        ULTIMATE_HEALTH("Whole Food, Plant-Based, SOS-Free"),
        HISTAMINE_FREE("No or low histamine contain");

        private final String label;

        private Diet(String value){
            label = value;
        }

        public String getLabel() {
            return label;
        }

        @Override
        public String toString() {
            return label;
        }
    }

    public enum Difficulty {
        EASY("Easy"),
        MEDIUM("Ok"),
        HARD("Hard");

        private final String label;

        private Difficulty(String value){
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

    public enum RequiredTimeInterval {
        FAST("30 min or less", 0, 30),
        MEDIUM("from 30 min to 1h", 30, 60),
        SLOW("more than 1h", 60, Integer.MAX_VALUE);

        private final String label;
        private final int start;
        private final int end;


        RequiredTimeInterval(String label, int start, int end) {
            this.label = label;
            this.start = start;
            this.end = end;
        }

        public String getLabel() {
            return label;
        }

        public int getStart() {
            return start;
        }

        public int getEnd() {
            return end;
        }

        @Override
        public String toString() {
            return label;
        }
    }


}
