package cz.mavenclu.cookbook.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Ingredient extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ingredient_seq_gen")
    @SequenceGenerator(name = "ingredient_seq_gen", sequenceName = "ingredient_id_seq", allocationSize = 1)
    private Long id;

    private String name;
    private String description;

    @Enumerated(EnumType.STRING)
    private Allergen allergen;

    @Enumerated(EnumType.STRING)
    private IngredientStatus status;

    public boolean isAllergen(){
        return allergen == null;
    }


    public enum IngredientStatus {
        PROPOSED ("proposed"),
        REVIEWED ("reviewed"){
            @Override
            public boolean isReviewed(){
                return true;
            }
        };

        private final String label;

        IngredientStatus(String label){
            this.label = label;
        }

        public boolean isReviewed(){
            return false;
        }
    }

    public boolean isReviewed(){
        return this.status.isReviewed();
    }

}
