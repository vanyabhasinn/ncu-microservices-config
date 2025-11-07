package com.ncu.library.borrowservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF for stateless APIs unless using cookies/auth forms
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/auth/authenticate").permitAll()   // let AuthService open
                .requestMatchers("/borrows/**").authenticated()      // secure the rest
            )
            .httpBasic(withDefaults()); // keep HTTP Basic, or replace with JWT/custom if you want

        return http.build();
    }
}


