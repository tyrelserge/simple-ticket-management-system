package io.ennov.simple_ticket_management_system.service;

import java.util.Optional;
import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import io.ennov.simple_ticket_management_system.entities.User;
import io.ennov.simple_ticket_management_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerUserDetailsService implements UserDetailsService {
    
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optional = userRepository.findByEmail(username);
        if (!optional.isPresent()) throw new UsernameNotFoundException("User not found");
        User user = optional.get();
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
	                    Collections.singletonList(new SimpleGrantedAuthority(user.getRole().toString())));
    }
}
