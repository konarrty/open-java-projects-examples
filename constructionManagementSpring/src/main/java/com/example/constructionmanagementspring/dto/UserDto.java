package com.example.constructionmanagementspring.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Principal;
import java.util.List;

@Data
@NoArgsConstructor
public class UserDto {
    private Long id;

    private String username;

    private String password;

    private String email;

    private String photoName;

    public boolean accountNonExpired;

    public boolean accountNonLocked;

    public boolean credentialsNonExpired;

    public boolean enabled;

    private List<RoleDto> roles;

    private EmployeeDto employee;

    private ProviderDto provider;

    public UserDto(Long id) {
        this.id = id;
    }
}
