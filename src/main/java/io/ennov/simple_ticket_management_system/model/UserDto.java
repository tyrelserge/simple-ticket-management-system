package io.ennov.simple_ticket_management_system.model;

import lombok.Data;

@Data
public class UserDto {
    private String username;
    private String email;
    private String password;
}
