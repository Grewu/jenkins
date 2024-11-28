package ru.senla.aspect;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import ru.senla.util.FakeAnnotatedService;

@SpringBootTest(
    classes = {
      LoggingAspect.class,
      AnnotationAwareAspectJAutoProxyCreator.class,
      FakeAnnotatedService.FakeAnnotatedClassService.class,
      FakeAnnotatedService.FakeAnnotatedMethodService.class
    })
public class LoggingAspectTest {

  @Autowired
  @Qualifier("fakeAnnotatedService.FakeAnnotatedMethodService")
  private FakeAnnotatedService fakeAnnotatedMethodService;

  @Autowired
  @Qualifier("fakeAnnotatedService.FakeAnnotatedClassService")
  private FakeAnnotatedService fakeAnnotatedSClassService;

  @SpyBean private LoggingAspect aspect;

  @Test
  void logClass() throws Throwable {
    fakeAnnotatedSClassService.fakeMethod("Logging");
    verify(aspect).log(any());
  }

  @Test
  void logMethod() throws Throwable {
    fakeAnnotatedMethodService.fakeMethod("Logging");
    verify(aspect).log(any());
  }
}
