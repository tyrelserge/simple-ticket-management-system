package io.ennov.simple_ticket_management_system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.
annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import io.ennov.simple_ticket_management_system.filter.JwtFilter;
import io.ennov.simple_ticket_management_system.service.CustomerUserDetailsService;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    
    private final CustomerUserDetailsService customerUserDetailsService;
	    private final JwtUtils jwtUtils;

	    @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

            return http.csrf(AbstractHttpConfigurer::disable)
                        .authorizeHttpRequests((auth) -> auth
                        .requestMatchers(HttpMethod.POST,"/users").permitAll()
                        .requestMatchers(HttpMethod.POST,"/users/login").permitAll()
                        .requestMatchers(HttpMethod.GET,"/users/**").hasAnyAuthority("ADMIN", "USER")
                        .requestMatchers("/users/**", "/tickets/**").hasAuthority("ADMIN")
                        .anyRequest().authenticated())
                    .addFilterBefore(new JwtFilter(customerUserDetailsService, jwtUtils), UsernamePasswordAuthenticationFilter.class)
                    .build();
	    }

	    @Bean
	    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception {
	        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
	        authenticationManagerBuilder.userDetailsService(customerUserDetailsService).passwordEncoder(passwordEncoder);
	        return authenticationManagerBuilder.build();
	    }

	    @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }

}
