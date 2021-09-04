package cz.mavenclu.cookbook.thymeleaf.dto;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class RecipeItemWebDto {
    private long id;
    @Min(0)
    private String amount;
    private Measure measure;
    private IngredientWebDto ingredient;


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
