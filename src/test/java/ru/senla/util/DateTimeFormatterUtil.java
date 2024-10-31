package ru.senla.util;

import java.time.format.DateTimeFormatter;

public class DateTimeFormatterUtil {
  public static final DateTimeFormatter DATE_TIME_FORMATTER =
      DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
}
