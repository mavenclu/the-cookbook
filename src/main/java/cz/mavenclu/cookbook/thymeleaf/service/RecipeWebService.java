package cz.mavenclu.cookbook.thymeleaf.service;


import cz.mavenclu.cookbook.thymeleaf.dto.RecipeWebDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
@Slf4j
@Service
public class RecipeWebService {

    private final WebClient webClient;

    public RecipeWebService(WebClient webClient) {
        this.webClient = webClient;
    }


    public RecipeWebDto getRecipeWithWebClient(Long id){
        log.info("getRecipeWithWebClient() - calling WebClient to look for recipe with ID: {}", id);
        Mono<RecipeWebDto> recipeMono =  webClient.get().uri("/cookbook/recipes/{id}", id)
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
}
