package cz.mavenclu.cookbook.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodHazard {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "food_seq_gen")
    @SequenceGenerator(name = "food_seq_gen", sequenceName = "food_id_seq", allocationSize = 1)
    private Long id;
    @ManyToOne
    private Recipe recipe;
    @ManyToOne
    private Allergen allergen;
}
