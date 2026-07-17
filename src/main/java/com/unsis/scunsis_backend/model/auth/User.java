package com.unsis.scunsis_backend.model.auth;

import com.unsis.scunsis_backend.model.enums.ERole;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ERole role;

    @Column(nullable = false)
    @Builder.Default
    private boolean mustChangePassword = true;
}
