package com.example.constructionmanagementspring.config;

import com.example.constructionmanagementspring.filter.JwtTokenFilter;
import com.example.constructionmanagementspring.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.type.jackson.JacksonJsonFormatMapper;
import org.hibernate.type.jackson.JacksonXmlFormatMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationConfiguration authConfiguration;

    private final JwtTokenFilter jwtTokenFilter;

    private final UserService userService;

    public SecurityConfig(AuthenticationConfiguration authConfiguration, JwtTokenFilter jwtTokenFilter, UserService userService) {
        this.authConfiguration = authConfiguration;
        this.jwtTokenFilter = jwtTokenFilter;
        this.userService = userService;
    }

    @Autowired
    public void authenticationManagerBuilder(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userService);
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests(exchanges -> exchanges
                        .requestMatchers("/api/auth/**", "/image/**", "/api/users/me").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/users/").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/providers/").permitAll()
                        .anyRequest().permitAll())
                .cors();

        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


}
