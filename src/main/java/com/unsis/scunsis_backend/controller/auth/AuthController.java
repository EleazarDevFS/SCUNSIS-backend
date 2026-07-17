package com.unsis.scunsis_backend.controller.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.getOrDefault("username", "");
        String password = credentials.getOrDefault("password", "");

        if ("admin".equals(username) && "admin".equals(password)) {
            return ResponseEntity.ok(Map.of("success", true, "message", "Inicio de sesion exitoso"));
        }

        return ResponseEntity.status(401).body(Map.of("success", false, "message", "Credenciales invalidas"));
    }
}
