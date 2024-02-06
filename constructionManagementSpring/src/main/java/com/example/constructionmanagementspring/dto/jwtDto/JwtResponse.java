package com.example.constructionmanagementspring.dto.jwtDto;

import com.example.constructionmanagementspring.model.Role;

import java.io.Serializable;
import java.util.List;

public class JwtResponse implements Serializable {

    private final String token;
    private final List<Role> roles;

    public JwtResponse(String token, List<Role> roles) {
        this.token = token;
        this.roles = roles;
    }

    public String getToken() {
        return this.token;
    }

    public List<Role> getRoles() {
        return roles;
    }
}