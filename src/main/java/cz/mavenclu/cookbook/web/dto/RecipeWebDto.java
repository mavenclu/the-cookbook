package cz.mavenclu.cookbook.web.dto;

import cz.mavenclu.cookbook.dto.RecipeItemDto;
import cz.mavenclu.cookbook.entity.Recipe;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Validated
public class RecipeWebDto {

    private Long id;

    @NotNull
    @Size(min = 3, max = 90)
    private String title;

    @NotNull
    @Size(min = 5, max = 160)
    private String description;

    @NotNull
    @Min(0)
    private int prepTime;
    @NotNull
    @Min(0)
    private int cookingTime;

    @NotNull
    private RecipeWebDto.Difficulty difficulty;

    @NotNull
    private Cuisine cuisine;
    @NotNull
    private List<Diet> diets;
    @Min(1)
    private int yields;

    @NotNull
    private List<RecipeItemWebDto> ingredients;

    @NotNull
    List<String> instructions;

    public enum Diet {
        VEGAN("Vegan"),
        OMNIVORE("Omnivore"),
        VEGETARIAN("Vegetarian"),
        GLUTEN_FREE("Gluten free"),
        DIARY_FREE("Diary free"),
        REFINE_SUGARS_FREE("Refine sugars free");

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

    public enum Cuisine {
        CZECH("Czech"),
        ASIAN("Asian"),
        MEDITERRANEAN("Mediterranean"),
        AMERICAN("American"),
        THAI("Thai"),
        INDIAN("Indian"),
        OTHER("other");

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



}
