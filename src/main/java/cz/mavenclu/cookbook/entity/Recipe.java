package cz.mavenclu.cookbook.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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

    @ElementCollection(targetClass = Diet.class)
    @Enumerated(EnumType.STRING)
    @JoinTable(name = "recipe_diet")
    @Column(name = "diet")
    private List<Diet> diets;

    @Enumerated(EnumType.STRING)
    private Cuisine cuisine;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @OneToMany(mappedBy = "recipe")
    private List<RecipeItem> recipeItems;


    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", instructions=" + instructions +
                ", prepTime=" + prepTime +
                ", cookingTime=" + cookingTime +
                ", yields=" + yields +
                ", diets=" + diets +
                ", cuisine=" + cuisine +
                ", difficulty=" + difficulty +
                ", recipeItems=" + recipeItems +
                '}';
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



}
