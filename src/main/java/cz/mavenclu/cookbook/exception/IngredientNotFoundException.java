package cz.mavenclu.cookbook.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.NoSuchElementException;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class IngredientNotFoundException extends NoSuchElementException {

    Logger log = LoggerFactory.getLogger(IngredientNotFoundException.class);

    public IngredientNotFoundException(Long id){
        super("Could not find ingredient with id: " + id);
        log.error("Ingredient with ID: {} was not found", id);
    }
}
