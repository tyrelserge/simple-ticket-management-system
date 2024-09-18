package io.ennov.simple_ticket_management_system.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import io.ennov.simple_ticket_management_system.entities.User;
import io.ennov.simple_ticket_management_system.model.Login;
import io.ennov.simple_ticket_management_system.model.UserDto;

@Service
public interface UserService {

    List<User> getAllUsers();

    User createUser(UserDto userData);

    User updateUser(Integer userId, UserDto userData);

    Map<String, String>  login(Login login);
    
}
