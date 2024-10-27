package ru.senla.model.entity.enums;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Arrays;
import java.util.List;

@Getter
public enum RoleType {

    ADMIN(Arrays.stream(PrivilegeType.values())
            .map(PrivilegeType::getGrantedAuthority)
            .toList()),

    USER(List.of(
            PrivilegeType.TASK_READ.getGrantedAuthority(),
            PrivilegeType.TASK_WRITE.getGrantedAuthority(),
            PrivilegeType.TASK_HISTORY_READ.getGrantedAuthority(),
            PrivilegeType.TASK_HISTORY_WRITE.getGrantedAuthority(),
            PrivilegeType.COMMENTS_READ.getGrantedAuthority(),
            PrivilegeType.COMMENTS_WRITE.getGrantedAuthority(),
            PrivilegeType.USER_PROFILE_READ.getGrantedAuthority(),
            PrivilegeType.USER_PROFILE_WRITE.getGrantedAuthority(),
            PrivilegeType.PROJECT_READ.getGrantedAuthority(),
            PrivilegeType.DEPARTMENT_READ.getGrantedAuthority(),
            PrivilegeType.POSITION_READ.getGrantedAuthority()
    )),

    GUEST(List.of(
            PrivilegeType.TASK_READ.getGrantedAuthority(),
            PrivilegeType.TASK_HISTORY_READ.getGrantedAuthority(),
            PrivilegeType.COMMENTS_READ.getGrantedAuthority(),
            PrivilegeType.USER_PROFILE_READ.getGrantedAuthority(),
            PrivilegeType.PROJECT_READ.getGrantedAuthority(),
            PrivilegeType.DEPARTMENT_READ.getGrantedAuthority(),
            PrivilegeType.POSITION_READ.getGrantedAuthority()
    ));

    private final List<GrantedAuthority> authorities;

    RoleType(List<GrantedAuthority> grantedAuthorities) {
        this.authorities = grantedAuthorities;
    }
}
