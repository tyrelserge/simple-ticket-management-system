package io.ennov.simple_ticket_management_system.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.ennov.simple_ticket_management_system.Constants;
import io.ennov.simple_ticket_management_system.config.JwtUtils;
import io.ennov.simple_ticket_management_system.entities.Ticket;
import io.ennov.simple_ticket_management_system.entities.User;
import io.ennov.simple_ticket_management_system.model.Login;
import io.ennov.simple_ticket_management_system.model.ResponseDto;
import io.ennov.simple_ticket_management_system.model.UserDto;
import io.ennov.simple_ticket_management_system.service.TicketService;
import io.ennov.simple_ticket_management_system.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final TicketService ticketService;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    
    @GetMapping("")
    public ResponseEntity<ResponseDto<List<User>>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(new ResponseDto<>(Constants.SUCCESS, users));
    }

    @GetMapping("/{userid}/ticket")
    public ResponseEntity<ResponseDto<List<Ticket>>> getUserTickets(@PathVariable("userid") Integer userId) {
        List<Ticket> tickets = ticketService.getUserTickets(userId);
        return ResponseEntity.ok(new ResponseDto<>(Constants.SUCCESS, tickets));
    }

    @PostMapping("")
    public ResponseEntity<ResponseDto<User>> createUser(@RequestBody UserDto userData) {
        User user = userService.createUser(userData);
        return user==null ? ResponseEntity.ok(new ResponseDto<>(Constants.ERROR, null)) :
            ResponseEntity.ok(new ResponseDto<>(Constants.SUCCESS, user));
    }
    
    @PutMapping("/{userid}")
    public ResponseEntity<ResponseDto<User>> updateUser(@PathVariable("userid") Integer userId, @RequestBody UserDto userData) {
        User user = userService.updateUser(userId, userData);
        return user==null ? ResponseEntity.ok(new ResponseDto<>(Constants.ERROR, null)) :
            ResponseEntity.ok(new ResponseDto<>(Constants.SUCCESS, user));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Login login) {

          try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword()));
            if (authentication.isAuthenticated()) {
                Map<String, String> authData = new HashMap<>();
                authData.put("token", jwtUtils.generateToken(login.getEmail()));
                authData.put("type", "Bearer");
                return ResponseEntity.ok(authData);
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalide username or password");

        } catch (AuthenticationException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalide username or password");
        }     
    }
    
}
