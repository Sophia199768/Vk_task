package org.itmo.vk_task.service;

import lombok.RequiredArgsConstructor;
import org.itmo.vk_task.model.LoginResponse;
import org.itmo.vk_task.security.UserPrincipal;
import org.itmo.vk_task.security.jwt.JwtIssuer;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtIssuer issuer;
    private final AuthenticationManager manager;

    public LoginResponse attemptLogin(String login, String password) {
        var auth = manager.authenticate(new UsernamePasswordAuthenticationToken(login, password));

        SecurityContextHolder.getContext().setAuthentication(auth);

        var principle = (UserPrincipal)auth.getPrincipal();

        var roles = principle.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        var token = issuer.issue(principle.getId(), principle.getLogin(), roles);
        return LoginResponse.builder().accessToken(token).build();
    }
}
