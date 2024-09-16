package io.ennov.simple_ticket_management_system.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.ennov.simple_ticket_management_system.Constants;
import io.ennov.simple_ticket_management_system.entities.Ticket;
import io.ennov.simple_ticket_management_system.entities.User;
import io.ennov.simple_ticket_management_system.model.ResponseDto;
import io.ennov.simple_ticket_management_system.model.UserDto;
import io.ennov.simple_ticket_management_system.service.TicketService;
import io.ennov.simple_ticket_management_system.service.UserService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequiredArgsConstructor
@RequestMapping("")
public class UserController {

    private final UserService userService;
    private final TicketService ticketService;
    
    @GetMapping("/users")
    public ResponseEntity<ResponseDto<List<User>>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(new ResponseDto<>(Constants.SUCCESS, users));
    }

    @GetMapping("/user/{userid}/tickets")
    public ResponseEntity<ResponseDto<List<Ticket>>> getUserTickets(@PathVariable("userid") Integer userId) {
        List<Ticket> tickets = ticketService.getUserTickets(userId);
        return ResponseEntity.ok(new ResponseDto<>(Constants.SUCCESS, tickets));
    }

    @PostMapping("/user")
    public ResponseEntity<ResponseDto<User>> createUser(@RequestBody UserDto userData) {
        User user = userService.createUser(userData);
        return user==null ? ResponseEntity.ok(new ResponseDto<>(Constants.ERROR, null)) :
            ResponseEntity.ok(new ResponseDto<>(Constants.SUCCESS, user));
    }
    
    @PutMapping("/user/{userid}")
    public ResponseEntity<ResponseDto<User>> updateUser(@PathVariable("userid") Integer userId, @RequestBody UserDto userData) {
        User user = userService.updateUser(userId, userData);
        return user==null ? ResponseEntity.ok(new ResponseDto<>(Constants.ERROR, null)) :
            ResponseEntity.ok(new ResponseDto<>(Constants.SUCCESS, user));
    }
}
