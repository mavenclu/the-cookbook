package cz.mavenclu.cookbook.config;

import cz.mavenclu.cookbook.security.SecurityInterceptorHandler;
import cz.mavenclu.cookbook.util.StringToEnumConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

    private final SecurityInterceptorHandler securityInterceptorHandler;

    public WebMvcConfig(SecurityInterceptorHandler security) {
        this.securityInterceptorHandler = security;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(securityInterceptorHandler);
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToEnumConverter());
    }
}
