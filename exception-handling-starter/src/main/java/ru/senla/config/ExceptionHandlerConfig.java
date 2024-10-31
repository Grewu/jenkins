package ru.senla.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.senla.handler.GlobalHandlerAdvice;

/**
 * The {@code ExceptionHandlerConfig} class is a Spring configuration class
 * that sets up the {@code GlobalHandlerAdvice} bean conditionally.
 *
 * <p>
 * This configuration class ensures that the {@code GlobalHandlerAdvice}
 * is only created if exception handling is enabled through the application properties.
 * </p>
 */
@Configuration
@ConditionalOnProperty(prefix = "spring.exception.handling", name = "enabled", havingValue = "true", matchIfMissing = true)
public class ExceptionHandlerConfig {

    @Bean
    @ConditionalOnMissingBean
    public GlobalHandlerAdvice globalHandlerAdvice() {
        return new GlobalHandlerAdvice();
    }
}
