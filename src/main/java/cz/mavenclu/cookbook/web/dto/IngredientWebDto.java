package cz.mavenclu.cookbook.web.dto;

import lombok.Data;

@Data
public class IngredientWebDto {
    private long id;
    private String name;
    private String description;
}
