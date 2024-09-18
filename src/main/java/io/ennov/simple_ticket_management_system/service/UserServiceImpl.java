package io.ennov.simple_ticket_management_system.service;

import java.util.*;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import io.ennov.simple_ticket_management_system.config.JwtUtils;
import io.ennov.simple_ticket_management_system.entities.User;
import io.ennov.simple_ticket_management_system.exception.ResourceNotFoundException;
import io.ennov.simple_ticket_management_system.model.Login;
import io.ennov.simple_ticket_management_system.model.UserDto;
import io.ennov.simple_ticket_management_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User createUser(UserDto userData) {
        
        Optional<User> optional = userRepository.findByEmail(userData.getEmail());

        if (optional.isPresent())
            throw new ResourceNotFoundException("User already exists");

        User user = new User();
        user.setUsername(userData.getUsername());
        user.setEmail(userData.getEmail());
        user.setPassword(passwordEncoder.encode(userData.getPassword()));
        user.setRole(userData.getRole().name().toString());
        
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Integer userId, UserDto userData) {
        Optional<User> optional = userRepository.findById(userId);

        if (!optional.isPresent())
            throw new ResourceNotFoundException("User not found");

        User user = optional.get();
        user.setUsername(userData.getUsername());
        user.setEmail(userData.getEmail());
        user.setPassword(passwordEncoder.encode(userData.getPassword()));
        user.setRole(userData.getRole().name().toString());
        
        return userRepository.save(user);

    }

    @Override
    public Map<String, String> login(Login login) {
        
         try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword()));
            
            if (!authentication.isAuthenticated()) 
                throw new ResourceNotFoundException("Invalid username or password");
            
                Map<String, String> authData = new HashMap<>();
                    authData.put("token", jwtUtils.generateToken(login.getEmail()));
                    authData.put("type", "Bearer");
                    return authData;

        } catch (AuthenticationException e) {
            log.error(e.getMessage());
            throw new ResourceNotFoundException("Invalid username or password");
        }

    }
    
}
