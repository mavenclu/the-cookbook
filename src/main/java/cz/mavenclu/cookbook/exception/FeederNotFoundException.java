package cz.mavenclu.cookbook.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.NoSuchElementException;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FeederNotFoundException extends NoSuchElementException {

    Logger log = LoggerFactory.getLogger(FeederNotFoundException.class);

    public FeederNotFoundException(Long id){
        super("Could not find feeder with id: " + id);
        log.error("Feeder with ID: {} was not found", id);
    }
}
