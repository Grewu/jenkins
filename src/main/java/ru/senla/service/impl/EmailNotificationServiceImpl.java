package ru.senla.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.senla.exception.EntityNotFoundException;
import ru.senla.model.entity.Task;
import ru.senla.model.entity.User;
import ru.senla.repository.api.UserRepository;
import ru.senla.service.api.NotificationService;

/**
 * The EmailNotificationServiceImpl class implements the NotificationService interface
 * to provide email notification functionality for tasks. It uses JavaMailSender
 * to send email notifications to users when tasks are due.
 *
 * @see NotificationService
 * @see Task
 * @see User
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmailNotificationServiceImpl implements NotificationService {

    @Value("${spring.mail.username}")
    private String emailFrom;

    private final JavaMailSender mailSender;
    private final UserRepository userRepository;

    /**
     * Sends a notification email to the user who created the specified task.
     * The email contains a reminder that the task is due tomorrow.
     *
     * @param task the task for which the notification is to be sent.
     * @throws EntityNotFoundException if the user who created the task cannot be found.
     */
    @Override
    public void sendNotification(Task task) {
        var user = userRepository.findById(task.getCreatedBy().getId())
                .orElseThrow(() -> new EntityNotFoundException(User.class, task.getCreatedBy().getId()));

        var message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Reminder: task \"" + task.getName() + "\" is due tomorrow!");
        message.setText("The task \"" + task.getName() + "\" must be completed by " + task.getDueDate() + ".");
        message.setFrom(emailFrom);

        mailSender.send(message);
    }
}
