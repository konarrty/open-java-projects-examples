package com.example.kpimanagment.permission;

import com.example.kpimanagment.model.Employee;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class EmployeePermissionEvaluator implements PermissionEvaluator {
    @Override
    public boolean hasPermission(Authentication auth,
                                 Object targetDomainObject, Object permission) {


        if (!(permission instanceof String))
            return false;

        if (!(auth.getPrincipal() instanceof UserWithPrivileges user))
            return false;

        if (!(targetDomainObject instanceof Employee employee))
            return false;

        return user.checkPrivilege(employee);

    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {

        return false;
    }

}