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
            PrivilegeType.READ.getGrantedAuthority(),
            PrivilegeType.WRITE.getGrantedAuthority()
    )),

    GUEST(List.of(
            PrivilegeType.READ.getGrantedAuthority()
    ));

    private final List<GrantedAuthority> authorities;

    RoleType(List<GrantedAuthority> grantedAuthorities) {
        this.authorities = grantedAuthorities;
    }
}
