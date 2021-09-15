package cz.mavenclu.cookbook.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Table(name = "allergen")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Allergen {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "allergen_seq_gen")
    @SequenceGenerator(name = "allergen_seq_gen", sequenceName = "allergen_id_seq", allocationSize = 1)
    private Long id;
    private String code;
    private String name;
}