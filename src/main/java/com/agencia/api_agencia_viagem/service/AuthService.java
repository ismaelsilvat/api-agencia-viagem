package com.agencia.api_agencia_viagem.service;

import com.agencia.api_agencia_viagem.model.Role;
import com.agencia.api_agencia_viagem.model.User;
import com.agencia.api_agencia_viagem.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;

    public String authenticate(String username, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            if (authentication.isAuthenticated()) {
                return "Authenticated successfully";
            } else {
                throw new RuntimeException("Authentication failed");
            }
        } catch (AuthenticationException e) {
            log.error(e.getMessage());
            throw new RuntimeException("Invalid username or password");
        }
    }

    public User register(String username, String password) {
        if (userService.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        Role defaultRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Default role not found"));

        User user = new User();
        user.setUsername(username);
        user.setRoles(Set.of(defaultRole));
        user.setPassword(passwordEncoder.encode(password));
        return userService.save(user);
    }
}
