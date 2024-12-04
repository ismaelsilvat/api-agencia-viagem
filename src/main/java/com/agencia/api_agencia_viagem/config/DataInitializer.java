package com.agencia.api_agencia_viagem.config;

import com.agencia.api_agencia_viagem.model.Role;
import com.agencia.api_agencia_viagem.model.User;
import com.agencia.api_agencia_viagem.repository.RoleRepository;
import com.agencia.api_agencia_viagem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) {
        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseGet(() -> {
                    Role role = new Role();
                    role.setName("ROLE_ADMIN");
                    return roleRepository.save(role);
                });

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseGet(() -> {
                    Role role = new Role();
                    role.setName("ROLE_USER");
                    return roleRepository.save(role);
                });

        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(new BCryptPasswordEncoder().encode("admin"));
            admin.setRoles(Set.of(adminRole));
            userRepository.save(admin);
            System.out.println("Usuário 'admin' criado com sucesso.");
        } else {
            System.out.println("Usuário 'admin' já existe. Pulando criação.");
        }

        if (userRepository.findByUsername("user").isEmpty()) {
            User user = new User();
            user.setUsername("user");
            user.setPassword(new BCryptPasswordEncoder().encode("user"));
            user.setRoles(Set.of(userRole));
            userRepository.save(user);
            System.out.println("Usuário 'user' criado com sucesso.");
        } else {
            System.out.println("Usuário 'user' já existe. Pulando criação.");
        }
    }
}
