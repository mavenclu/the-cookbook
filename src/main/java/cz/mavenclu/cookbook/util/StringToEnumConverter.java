package cz.mavenclu.cookbook.util;

import cz.mavenclu.cookbook.entity.Recipe;
import cz.mavenclu.cookbook.exception.IllegalEnumArgumentException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

@Slf4j
public class StringToEnumConverter implements Converter<String, Recipe.Cuisine> {

    @Override
    public Recipe.Cuisine convert(String source) {
        try {

            return Recipe.Cuisine.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            log.warn("convert - failed to convert source: {} to enum", source);
            throw new IllegalEnumArgumentException(source);
        }
    }
}
