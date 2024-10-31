package ru.senla.model.entity.enums;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Getter
public enum PrivilegeType {
  COMMENTS_READ(new SimpleGrantedAuthority("comments:read")),
  COMMENTS_WRITE(new SimpleGrantedAuthority("comments:write")),
  COMMENTS_DELETE(new SimpleGrantedAuthority("comments:delete")),

  TASK_READ(new SimpleGrantedAuthority("task:read")),
  TASK_WRITE(new SimpleGrantedAuthority("task:write")),
  TASK_DELETE(new SimpleGrantedAuthority("task:delete")),

  TASK_HISTORY_READ(new SimpleGrantedAuthority("task_history:read")),
  TASK_HISTORY_WRITE(new SimpleGrantedAuthority("task_history:write")),
  TASK_HISTORY_DELETE(new SimpleGrantedAuthority("task_history:delete")),

  USER_PROFILE_READ(new SimpleGrantedAuthority("user_profile:read")),
  USER_PROFILE_WRITE(new SimpleGrantedAuthority("user_profile:write")),
  USER_PROFILE_DELETE(new SimpleGrantedAuthority("user_profile:delete")),

  USER_READ(new SimpleGrantedAuthority("user:read")),
  USER_WRITE(new SimpleGrantedAuthority("user:write")),
  USER_DELETE(new SimpleGrantedAuthority("user:delete")),

  PROJECT_READ(new SimpleGrantedAuthority("project:read")),
  PROJECT_WRITE(new SimpleGrantedAuthority("project:write")),
  PROJECT_DELETE(new SimpleGrantedAuthority("project:delete")),

  DEPARTMENT_READ(new SimpleGrantedAuthority("department:read")),
  DEPARTMENT_WRITE(new SimpleGrantedAuthority("department:write")),
  DEPARTMENT_DELETE(new SimpleGrantedAuthority("department:delete")),

  POSITION_READ(new SimpleGrantedAuthority("position:read")),
  POSITION_WRITE(new SimpleGrantedAuthority("position:write")),
  POSITION_DELETE(new SimpleGrantedAuthority("position:delete"));

  private final GrantedAuthority grantedAuthority;

  PrivilegeType(GrantedAuthority grantedAuthority) {
    this.grantedAuthority = grantedAuthority;
  }

  public GrantedAuthority getGrantedAuthority() {
    return grantedAuthority;
  }
}
