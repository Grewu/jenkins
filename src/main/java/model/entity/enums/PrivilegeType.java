package model.entity.enums;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Getter
public enum PrivilegeType {
    READ(new SimpleGrantedAuthority("privilege:read")),
    WRITE(new SimpleGrantedAuthority("privilege:write")),
    DELETE(new SimpleGrantedAuthority("privilege:delete"));

    private final GrantedAuthority grantedAuthority;

    PrivilegeType(GrantedAuthority grantedAuthority) {
        this.grantedAuthority = grantedAuthority;
    }

    public GrantedAuthority getGrantedAuthority() {
        return grantedAuthority;
    }
}
