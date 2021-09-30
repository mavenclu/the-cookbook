package cz.mavenclu.cookbook.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IllegalEnumArgumentException extends IllegalArgumentException {

    public IllegalEnumArgumentException(String source) {
        super(source);
        log.warn("Failed to convert value: {} to enum ", source);
    }
}
