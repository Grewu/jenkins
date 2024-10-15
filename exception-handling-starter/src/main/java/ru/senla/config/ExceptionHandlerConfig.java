package ru.senla.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.senla.handler.GlobalHandlerAdvice;

@Configuration
@ConditionalOnProperty(prefix = "spring.exception.handling", name = "enabled", havingValue = "true", matchIfMissing = true)
public class ExceptionHandlerConfig {

    @Bean
    @ConditionalOnMissingBean
    public GlobalHandlerAdvice globalHandlerAdvice() {
        return new GlobalHandlerAdvice();
    }
}
