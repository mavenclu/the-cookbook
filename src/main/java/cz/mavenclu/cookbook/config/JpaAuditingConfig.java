package cz.mavenclu.cookbook.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;





@Slf4j
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JpaAuditingConfig {

    @Bean
    public AuditorAware<String> auditorProvider() {
        log.info("GETTING A USERNAME FROM A TOKEN");
        return () -> {
            if (SecurityContextHolder.getContext().getAuthentication() != null) {
                Jwt jwtToken = (Jwt) SecurityContextHolder.getContext().getAuthentication();
                log.info("auditorProvider - got token");
                String username = jwtToken.getClaimAsString("email");
                log.info("auditorProvider - got sub claim substring: {}", username);
                return java.util.Optional.of(username);
            } else {
                return java.util.Optional.of("Unknown");
            }
        };
    }


}
