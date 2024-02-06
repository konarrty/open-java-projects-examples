package com.example.tourmanagement.utils;

import com.example.tourmanagement.model.entity.Agent;
import com.example.tourmanagement.model.entity.Client;
import com.example.tourmanagement.model.entity.TourOperator;
import com.example.tourmanagement.model.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class ClientContextUtils {

    public static Client getClient() {

        User user = (User) SecurityContextHolder
                .getContext().getAuthentication()
                .getPrincipal();

        return user.getClient();
    }

    public static Agent getAgent() {

        User user = (User) SecurityContextHolder
                .getContext().getAuthentication()
                .getPrincipal();

        return user.getAgent();
    }

    public static TourOperator getOperator() {

        User user = (User) SecurityContextHolder
                .getContext().getAuthentication()
                .getPrincipal();

        return user.getOperator();
    }
}
