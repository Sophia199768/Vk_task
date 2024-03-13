package org.itmo.vk_task.service;
import org.itmo.vk_task.pojo.User;
import org.itmo.vk_task.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserService {

    @Autowired
    private UserRepository repository;
    public Optional<User> findUserByEmail(String email) {

        return repository.findUserByEmail(email);
    }

    public User createNewUser(String email, String password, String role) {
        var encoder = new BCryptPasswordEncoder();
        if (repository.existsByEmail(email)) throw new BadCredentialsException("");
        var user = new User();
        user.setEmail(email);
        user.setPassword(encoder.encode(password));
        user.setRole(role);
        return repository.save(user);
    }
}
