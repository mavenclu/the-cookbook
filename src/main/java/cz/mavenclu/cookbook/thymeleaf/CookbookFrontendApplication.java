package cz.mavenclu.cookbook.thymeleaf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
public class CookbookFrontendApplication {

    public static void main(String[] args) {
        SpringApplication.run(CookbookFrontendApplication.class, args);
    }

}
