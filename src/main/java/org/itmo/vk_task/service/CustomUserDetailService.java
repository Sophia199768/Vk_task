package org.itmo.vk_task.service;

import lombok.RequiredArgsConstructor;
import org.itmo.vk_task.security.UserPrincipal;
import org.itmo.vk_task.service.UserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userService.findUserByEmail(username).orElseThrow();
        return UserPrincipal.builder().id(user.getId())
                .login(user.getEmail())
                .authorities(List.of(new SimpleGrantedAuthority(user.getRole())))
                .password(user.getPassword()).build();
    }
}
