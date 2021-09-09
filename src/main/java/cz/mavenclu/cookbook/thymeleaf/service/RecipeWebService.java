package cz.mavenclu.cookbook.thymeleaf.service;



import cz.mavenclu.cookbook.thymeleaf.dto.RecipeWebDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
public class RecipeWebService {

    private final WebClient webClient;
//    private final ResourceApiConfig urlConfig;
    @Value("${cookbook.rest.resource.all-recipes}")
    private String allRecipesUrl;


    public RecipeWebService(WebClient webClient
//            , ResourceApiConfig urlConfig
    ) {
        this.webClient = webClient;
//        this.urlConfig = urlConfig;
    }


    public RecipeWebDto getRecipeWithWebClient(Long id){
        log.info("getRecipeWithWebClient() - calling WebClient to look for recipe with ID: {}", id);
        Mono<RecipeWebDto> recipeMono =  webClient.get().uri("cookbook/recipes/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(response -> {
                    if (response.statusCode().is2xxSuccessful()){
                        return response.bodyToMono(RecipeWebDto.class);
                    } else
                        return Mono.empty();

                });

        log.info("getRecipeWithWebClient() - WebClient retrieved: {}", recipeMono.toString());
        RecipeWebDto recipe = recipeMono.block();
        log.info("getRecipeWithWebClient() - got: {}", recipe);
        return recipe;
    }

    public List<RecipeWebDto> getAllRecipes(){
        Mono<List<RecipeWebDto>> response = webClient
                .get()
                .uri(allRecipesUrl)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {
                });
        List<RecipeWebDto> recipes = response.block();
        log.info("getAllRecipes() - ");
        return recipes;
    }

}
