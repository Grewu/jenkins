package ru.senla.model.entity.enums;

public enum EnumActionDescription {
  ENTITY_CREATED("%s was created"),
  ENTITY_UPDATED("%s was updated"),
  ENTITY_DELETED("%s was deleted");

  private final String descriptionTemplate;

  EnumActionDescription(String descriptionTemplate) {
    this.descriptionTemplate = descriptionTemplate;
  }

  public <T> String getDescription(Class<T> entityName) {
    return String.format(descriptionTemplate, entityName.getSimpleName());
  }
}
