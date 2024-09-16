package io.ennov.simple_ticket_management_system.service;

import java.util.List;

import org.springframework.stereotype.Service;

import io.ennov.simple_ticket_management_system.entities.User;
import io.ennov.simple_ticket_management_system.model.UserDto;

@Service
public interface UserService {

    List<User> getAllUsers();

    User createUser(UserDto userData);

    User updateUser(Integer userId, UserDto userData);
    
}
