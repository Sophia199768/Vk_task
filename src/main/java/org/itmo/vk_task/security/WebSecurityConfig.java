package org.itmo.vk_task.security;

import lombok.RequiredArgsConstructor;
import org.itmo.vk_task.security.jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final String ADMIN = "ADMIN";
    private final String POSTS = "POSTS";
    private final String USERS = "USERS";
    private final String ALBUMS = "ALBUMS";


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(withDefaults())
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(request -> request
                .requestMatchers("/user/**").permitAll()
                        .requestMatchers("/api/posts/**").hasAnyRole(ADMIN, POSTS)
                        .requestMatchers("/api/users/**").hasAnyRole(ADMIN, USERS)
                        .requestMatchers("/api/albums/**").hasAnyRole(ADMIN, ALBUMS)
                        .anyRequest().authenticated())
                .build();
    }
}
