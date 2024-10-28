package ru.senla.service.impl;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import ru.senla.data.TaskTestData;
import ru.senla.data.UserTestData;
import ru.senla.repository.api.UserRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmailNotificationServiceImplTest {
    @Mock
    private JavaMailSender mailSender;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private EmailNotificationServiceImpl emailNotificationService;

    @Nested
    class SendNotification {
        @Test
        void testSendNotificationShouldSendMessage() {
            //given
            var user = UserTestData.builder().build().buildUser();
            var task = TaskTestData.builder().build().buildTask();
            var messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);

            //when
            when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

            //then
            emailNotificationService.sendNotification(task);

            verify(mailSender).send(messageCaptor.capture());
            assertThat(messageCaptor.getValue().getTo()).containsExactly(user.getEmail());
        }
    }
}
