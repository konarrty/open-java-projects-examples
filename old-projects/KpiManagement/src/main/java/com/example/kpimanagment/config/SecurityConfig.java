package com.example.kpimanagment.config;

import com.example.kpimanagment.handler.CustomAccessDeniedHandler;
import com.example.kpimanagment.handler.auth.SimpleAuthenticationFailureHandler;
import com.example.kpimanagment.handler.auth.SimpleAuthenticationSuccessHandler;
import com.example.kpimanagment.permission.EmployeePermissionEvaluator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final EmployeePermissionEvaluator evaluator;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;


    @Primary
    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setTaskDecorator(new ContextCopyingDecorator());
        threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        threadPoolTaskExecutor.initialize();

        return threadPoolTaskExecutor;
    }


    @Bean
    public MethodSecurityExpressionHandler createExpressionHandler() {

        DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();

        expressionHandler.setPermissionEvaluator(evaluator);
        return expressionHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(exchanges -> exchanges
                        .requestMatchers(HttpMethod.GET, "assets/**", "bootstrap3/**", "js/login.js", "js/registration.js", "js/errors.js").permitAll()
                        .requestMatchers("/registration/**").permitAll()
                        .requestMatchers("/templates/tr.html").authenticated()
                        .requestMatchers("/templates/**").denyAll()
                        .anyRequest().authenticated()
                ).formLogin(configurer ->
                        configurer.loginPage("/login").permitAll()
                                .successHandler(new SimpleAuthenticationSuccessHandler())
                                .failureHandler((new SimpleAuthenticationFailureHandler()))
                )
                .logout(logoutCustomizer -> logoutCustomizer.logoutSuccessUrl("/"))
                .exceptionHandling(ex -> ex.accessDeniedHandler(customAccessDeniedHandler));


        return http.build();
    }
}

