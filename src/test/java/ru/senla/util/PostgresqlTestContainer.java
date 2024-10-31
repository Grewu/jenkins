package ru.senla.util;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
public class PostgresqlTestContainer {

  private static final PostgreSQLContainer<?> container =
      new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"));

  @DynamicPropertySource
  static void setUrl(DynamicPropertyRegistry registry) {
    container.start();
    registry.add("spring.datasource.url", container::getJdbcUrl);
  }
}
