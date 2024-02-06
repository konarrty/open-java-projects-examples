package com.example.kpimanagment.enums;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@NoArgsConstructor
@Getter
public enum ERole implements GrantedAuthority {

    ROLE_USER("Сотрудник"),

    ROLE_CURATOR("Куратор"),

    ROLE_ADMIN("Администратор");

    private String name;

    ERole(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name();
    }

}