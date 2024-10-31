package ru.senla.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for OpenAPI documentation.
 *
 * <p>This class configures OpenAPI documentation for the Task Management System. It sets the title
 * and version of the API and defines security schemes for bearer token authentication using JWT.
 */
@Configuration
@OpenAPIDefinition(info = @Info(title = "Task management system", version = "1.0.0"))
@SecurityScheme(
    name = "Bearer Authentication",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    scheme = "bearer")
public class OpenApiConfig {}
