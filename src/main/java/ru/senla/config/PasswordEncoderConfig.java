package ru.senla.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration class for password encoding.
 *
 * <p>This class provides a bean for encoding passwords using the BCrypt algorithm. It is used for
 * securely hashing passwords before storing them in the database.
 */
@Configuration
public class PasswordEncoderConfig {

  /**
   * Creates a bean for the PasswordEncoder using BCrypt.
   *
   * @return a PasswordEncoder instance that uses BCrypt for hashing passwords
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
