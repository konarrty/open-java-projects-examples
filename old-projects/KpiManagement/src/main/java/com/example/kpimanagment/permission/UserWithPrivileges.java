package com.example.kpimanagment.permission;

import com.example.kpimanagment.model.Employee;
import com.example.kpimanagment.model.Privilege;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class UserWithPrivileges extends User {

    private final Collection<Privilege> privileges;

    public UserWithPrivileges(String username, String password, Collection<? extends GrantedAuthority> authorities, Collection<Privilege> privileges) {
        super(username, password, authorities);
        this.privileges = privileges;
    }

    public boolean checkPrivilege( Employee employee) {

        return privileges.stream()
                .anyMatch(privilege -> privilege.getEmployee().getId().equals(employee.getId()));
    }
}
