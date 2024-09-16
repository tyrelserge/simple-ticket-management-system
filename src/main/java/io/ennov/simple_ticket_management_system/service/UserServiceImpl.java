package io.ennov.simple_ticket_management_system.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import io.ennov.simple_ticket_management_system.entities.User;
import io.ennov.simple_ticket_management_system.model.UserDto;
import io.ennov.simple_ticket_management_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User createUser(UserDto userData) {
        
        Optional<User> optional = userRepository.findByEmail(userData.getEmail());

        if (optional.isPresent())
            throw new RuntimeException("User already exists");

        User user = new User();
        user.setUsername(userData.getUsername());
        user.setEmail(userData.getEmail());
        user.setPassword(userData.getPassword());
        
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Integer userId, UserDto userData) {
        Optional<User> optional = userRepository.findById(userId);

        if (!optional.isPresent())
            throw new RuntimeException("User not found");

        User user = optional.get();
        user.setUsername(userData.getUsername());
        user.setEmail(userData.getEmail());
        user.setPassword(userData.getPassword());

        return userRepository.save(user);

    }
    
}
