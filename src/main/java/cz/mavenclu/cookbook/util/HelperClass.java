package cz.mavenclu.cookbook.util;

import cz.mavenclu.cookbook.dao.IngredientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HelperClass {

    @Bean
    public CommandLineRunner run (IngredientRepository ingredientRepository) {
        return args -> {

        };
    }


    public  String getPrincipalSub(Jwt idToken){
        return (idToken != null) ? idToken.getClaimAsString("sub").substring(6) : "";

    }

}
