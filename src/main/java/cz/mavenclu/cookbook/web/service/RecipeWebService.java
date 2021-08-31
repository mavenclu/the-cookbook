package cz.mavenclu.cookbook.web.service;


import cz.mavenclu.cookbook.dto.RecipeResponseDto;
import cz.mavenclu.cookbook.web.dto.RecipeWebDto;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class RecipeWebService {

    private final WebClient webClient;

    public RecipeWebService(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://localhost:8080").build();
    }

    public Mono<RecipeWebDto> getRecipe(Long id){
        return webClient.get().uri("/cookbook/recipes/{id}", id)
                .retrieve().bodyToMono(RecipeWebDto.class);
    }
}
