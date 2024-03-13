package org.itmo.vk_task.controller;

import lombok.RequiredArgsConstructor;
import org.itmo.vk_task.model.CreateRequest;
import org.itmo.vk_task.model.LoginResponse;
import org.itmo.vk_task.service.AuthService;
import org.itmo.vk_task.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final AuthService serviceAuth;
    private final UserService serviceUser;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody CreateRequest request) {
        return serviceAuth.attemptLogin(request.getLogin(), request.getPassword());
    }

    @PostMapping("/create")
    public LoginResponse create(@RequestBody CreateRequest request) {
        var user = serviceUser.createNewUser(request.getLogin(), request.getPassword(), request.getRole());
        var UnEncodedPassword = request.getPassword();
        return serviceAuth.attemptLogin(user.getEmail(),UnEncodedPassword);
    }
}
