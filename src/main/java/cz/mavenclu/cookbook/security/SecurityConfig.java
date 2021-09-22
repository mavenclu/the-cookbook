package cz.mavenclu.cookbook.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class SecurityConfig implements WebMvcConfigurer {

    private final SecurityInterceptorHandler securityInterceptorHandler;

    public SecurityConfig(SecurityInterceptorHandler security) {
        this.securityInterceptorHandler = security;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(securityInterceptorHandler);
    }
}
