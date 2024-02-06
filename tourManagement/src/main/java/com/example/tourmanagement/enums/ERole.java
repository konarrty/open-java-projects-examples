package com.example.tourmanagement.enums;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@NoArgsConstructor
@Getter
public enum ERole implements GrantedAuthority {

    ROLE_TOURIST("Турист"),
    ROLE_AGENT("Агент"),
    ROLE_TOUR_OPERATOR("Оператор");

    private String name;

    ERole(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name();
    }
}
