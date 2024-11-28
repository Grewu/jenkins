package ru.senla.service.api;

import ru.senla.model.entity.Task;

/**
 * Service interface for handling notifications related to tasks.
 *
 * <p>This interface defines the operations for sending notifications about task events, such as
 * creation, updates, or other significant changes.
 */
public interface NotificationService {

  /**
   * Sends a notification for the specified task.
   *
   * @param task the task for which the notification is to be sent
   */
  void sendNotification(Task task);
}
