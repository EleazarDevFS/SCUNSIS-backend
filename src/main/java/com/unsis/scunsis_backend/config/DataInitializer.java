package com.unsis.scunsis_backend.config;

import com.unsis.scunsis_backend.model.auth.User;
import com.unsis.scunsis_backend.model.enums.ERole;
import com.unsis.scunsis_backend.repository.auth.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (!userRepository.existsByUsername("admin")) {
            User admin = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin"))
                    .role(ERole.ADMIN)
                    .build();
            userRepository.save(admin);
            System.out.println("Usuario admin creado por defecto (admin/admin)");
        }

        if (!userRepository.existsByUsername("capturista")) {
            User capturista = User.builder()
                    .username("capturista")
                    .password(passwordEncoder.encode("capturista"))
                    .role(ERole.CAPTURISTA)
                    .build();
            userRepository.save(capturista);
            System.out.println("Usuario capturista creado por defecto (capturista/capturista)");
        }
    }
}
