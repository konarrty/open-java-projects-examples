package com.example.kpimanagment.utils;

import com.example.kpimanagment.enums.ERole;
import com.example.kpimanagment.model.Privilege;
import org.springframework.stereotype.Component;

@Component
public class PrivilegeFilterUtils {

    public boolean notAdmin(Privilege privilege) {

        return !privilege.getUser().getRole().equals(ERole.ROLE_ADMIN) && !privilege.getEmployee().getUser().getRole().equals(ERole.ROLE_ADMIN);

    }

    public boolean onlyCurator(Privilege privilege) {

        return !privilege.getEmployee().getUser().getRole().equals(ERole.ROLE_ADMIN);

    }

    public boolean notMe(Privilege privilege) {

        return !privilege.getEmployee().getUser().equals(privilege.getUser());
    }
}

