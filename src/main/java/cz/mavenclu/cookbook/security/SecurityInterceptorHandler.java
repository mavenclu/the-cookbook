package cz.mavenclu.cookbook.security;


import cz.mavenclu.cookbook.dto.ChefDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.HandlerInterceptor;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

import static reactor.core.publisher.Mono.error;

@Slf4j
@Service
public class SecurityInterceptorHandler implements HandlerInterceptor {

    @Value("${authentication.userinfo-endpoint}")
    private String userInfoUrl;

    private final WebClient webClient;

    public SecurityInterceptorHandler(WebClient webClient) {
        this.webClient = webClient;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        //Check if  valid format of Authorization header
        if (!isAuthorizationHeaderValid(request)){
            log.info("preHandle - validation of request authorization header failed. Returning false.");
            response.setStatus(401);
            return false;
        }

        // check if token is null
        String token = getAuthorizationToken(request);
        if (token == null){
            log.info("preHandle - Authorization token is missing. Returning false.");
            response.setStatus(401);
            return false;
        }
        log.info("preHandle - retrieved token: {}", token);
        try {
            ChefDto chef = getUserByToken(token);
            log.info("preHandle - got user: {}", chef);
            injectUserIntoRequest(request, chef);
            response.setStatus(200);
            return true;

        }catch (NoSuchElementException noSuchElementException) {
            log.warn("preHandle - user not found. Returning false.");
            response.setStatus(401);
            return false;
        }
    }

    //getting user info with token
    private ChefDto getUserByToken(String token){
        log.info("getUserByToken - looking for user with param token: {}", token);

        Mono<ChefDto> monoChef = webClient
                .get()
                .uri(userInfoUrl)
                .headers(header -> header.setBearerAuth(token))
                .retrieve()
                .onStatus(
                        Predicate.not(HttpStatus::is2xxSuccessful), response ->
                                error(new NoSuchElementException("User not found"))
                )

                .bodyToMono(new ParameterizedTypeReference<>() {
                });

        log.info("getUserByToken - got response: {}", monoChef);
        ChefDto chef = monoChef.block();
        log.info("getUserByToken - got ChefDto: {}", chef);
        if (chef != null) {
            String id = chef.getId().substring(6);
            chef.setId(id);
            log.info("getUserByToken - token added. got user: {}", chef);
        }
        return chef;

    }

    private boolean isAuthorizationHeaderValid(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (authorization == null) {
            log.info("isAuthorizationHeaderValid - Authorization header key is missing (returning false)");
            return false;
        } else if (!authorization.toLowerCase().startsWith("bearer ")) {
            log.info("isAuthorizationHeaderValid - Authorization value has wrong format (returning false)");
            return false;
        }
        log.info("isAuthorizationHeaderValid - Authorization header successfully validated (returning true)");
        return true;
    }

    private String getAuthorizationToken(HttpServletRequest request) {
        log.info("getAuthorizationToken - got authorization header: {}", request.getHeader(HttpHeaders.AUTHORIZATION));
        String token = request.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
        if (token.isBlank()) {
            log.info("getAuthorizationToken - authorization token is missing (returning null)");
            return null;
        }
        log.info("getAuthorizationToken - got token: {}", token);
        return token;

    }
    private void injectUserIntoRequest(HttpServletRequest request, ChefDto chef){
        request.setAttribute("chef", chef);
    }


}
