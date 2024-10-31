package ru.senla.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The {@code Logging} annotation is used to indicate that a class or method
 * should be logged.
 * <p>
 * When this annotation is used, an aspect of logging may be implemented
 * that automatically tracks the execution of methods and generates
 * corresponding log messages.
 * </p>
 *
 * <p>
 * {@code Logging} can be applied to both classes and methods.
 * </p>
 *
 * @see ru.senla.aspect.LoggingAspect
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Logging {
}
