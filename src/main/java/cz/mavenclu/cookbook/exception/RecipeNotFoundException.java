package cz.mavenclu.cookbook.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.NoSuchElementException;
@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecipeNotFoundException extends NoSuchElementException {

    Logger log = LoggerFactory.getLogger(RecipeNotFoundException.class);

    public RecipeNotFoundException(Long id){
        super("Could not find recipe with id: " + id);
        log.error("Recipe with ID: {} was not found", id);
    }
}
