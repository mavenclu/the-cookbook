package cz.mavenclu.cookbook.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;

/**
 * validate that the JWT is intended for your API by checking the aud claim of the JWT
 */
@Slf4j
class AudienceValidator implements OAuth2TokenValidator<Jwt> {

    private final String audience;

    AudienceValidator(String audience) {
        this.audience = audience;
    }

    public OAuth2TokenValidatorResult validate(Jwt jwt) {
        log.info("validate JWT, token audience: {},  claims: {}", jwt.getAudience(),  jwt.getClaims());
        OAuth2Error error = new OAuth2Error("invalid_token", "The required audience is missing", null);

        if (jwt.getAudience().contains(audience)) {
            log.info("validate JWT - successfully validated");
            return OAuth2TokenValidatorResult.success();
        }
        log.error("validate JWT - token invalid");
        return OAuth2TokenValidatorResult.failure(error);
    }



}