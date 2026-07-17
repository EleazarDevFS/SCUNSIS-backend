package com.unsis.scunsis_backend.controller.auth;

import com.unsis.scunsis_backend.model.auth.User;
import com.unsis.scunsis_backend.repository.auth.IUserRepository;
import com.unsis.scunsis_backend.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final IUserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.getOrDefault("username", "");
        String password = credentials.getOrDefault("password", "");

        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            return ResponseEntity.status(401).body(Map.of(
                    "success", false,
                    "message", "Credenciales invalidas"
            ));
        }

        String token = jwtUtil.generateToken(user.getUsername(), user.getRole().name());

        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Inicio de sesion exitoso",
                "token", token,
                "username", user.getUsername(),
                "role", user.getRole().name()
        ));
    }
}
