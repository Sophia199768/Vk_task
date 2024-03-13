package org.itmo.vk_task.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginRequest {
    private String login;
    private String password;
}
