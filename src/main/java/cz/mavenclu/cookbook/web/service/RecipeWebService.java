package cz.mavenclu.cookbook.web.service;


import cz.mavenclu.cookbook.web.dto.RecipeWebDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
                    } else if (response.statusCode().is4xxClientError()){
                        return Mono.just("Something went wrong");
                    } else {
                        return Mono.error()
                    }
                });

        log.info("getRecipeWithWebClient() - WebClient retrieved: {}", recipeMono.toString());
        RecipeWebDto recipe = recipeMono.block();
        log.info("getRecipeWithWebClient() - got: {}", recipe);
        return recipe;
    }
}
