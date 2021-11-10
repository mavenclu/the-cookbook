package cz.mavenclu.cookbook.service;

import cz.mavenclu.cookbook.entity.Allergen;
import cz.mavenclu.cookbook.entity.Recipe;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RecipeServiceTest {

    private List<Recipe> recipes;
    private List<Allergen> allergens;

    @BeforeEach
    public void setup(){

    }

    @AfterEach
    public void tearDown(){
        recipes = null;
        allergens = null;
    }

    @Test
    void givenNoAllergensMatchThenPredicateShouldReturnTrue(){

    }


}