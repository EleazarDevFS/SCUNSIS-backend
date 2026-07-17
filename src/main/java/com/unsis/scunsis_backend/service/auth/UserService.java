package com.unsis.scunsis_backend.service.auth;

import com.unsis.scunsis_backend.dto.request.auth.UserRequest;
import com.unsis.scunsis_backend.dto.response.auth.UserResponse;
import com.unsis.scunsis_backend.model.auth.User;
import com.unsis.scunsis_backend.model.enums.ERole;
import com.unsis.scunsis_backend.repository.auth.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<UserResponse> findAll() {
        return userRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public Optional<UserResponse> findById(Long id) {
        return userRepository.findById(id).map(this::toResponse);
    }

    public UserResponse create(UserRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("El nombre de usuario ya existe");
        }
        ERole role;
        try {
            role = ERole.valueOf(request.getRole().trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Rol invalido: " + request.getRole());
        }
        User user = User.builder()
                .username(request.getUsername().trim())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .mustChangePassword(true)
                .build();
        return toResponse(userRepository.save(user));
    }

    public void changePassword(Long id, String newPassword) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setMustChangePassword(false);
        userRepository.save(user);
    }

    public UserResponse update(Long id, UserRequest request, String currentUsername) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        if (user.getUsername().equals(currentUsername)) {
            throw new IllegalArgumentException("No puedes editarte a ti mismo");
        }
        String newUsername = request.getUsername().trim();
        if (!newUsername.equals(user.getUsername()) && userRepository.existsByUsername(newUsername)) {
            throw new IllegalArgumentException("El nombre de usuario ya existe");
        }
        ERole role;
        try {
            role = ERole.valueOf(request.getRole().trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Rol invalido: " + request.getRole());
        }
        user.setUsername(newUsername);
        user.setRole(role);
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        return toResponse(userRepository.save(user));
    }

    public void deleteById(Long id, String currentUsername) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        if (user.getUsername().equals(currentUsername)) {
            throw new IllegalArgumentException("No puedes eliminarte a ti mismo");
        }
        userRepository.deleteById(user.getId());
    }

    private UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .role(user.getRole().name())
                .mustChangePassword(user.isMustChangePassword())
                .build();
    }
}
