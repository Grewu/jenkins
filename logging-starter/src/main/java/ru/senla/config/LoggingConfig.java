package ru.senla.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.senla.aspect.LoggingAspect;

/**
 * The {@code LoggingConfig} class is a Spring configuration class
 * that sets up the {@code LoggingAspect} bean conditionally.
 *
 * <p>
 * This configuration class ensures that the {@code LoggingAspect}
 * is only created if there is no existing bean of the same type
 * and if logging is enabled through the application properties.
 * </p>
 */
@Configuration
@ConditionalOnMissingBean(LoggingAspect.class)
@ConditionalOnProperty(prefix = "spring.logging", name = "enabled", havingValue = "true", matchIfMissing = true)
public class LoggingConfig {

    @Bean
    public LoggingAspect loggingAspect() {
        return new LoggingAspect();
    }
}
