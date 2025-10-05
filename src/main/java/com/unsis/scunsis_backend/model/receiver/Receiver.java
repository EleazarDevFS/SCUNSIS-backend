package com.unsis.scunsis_backend.model.receiver;

import java.util.ArrayList;
import java.util.List;

import com.unsis.scunsis_backend.model.proof.Proof;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "receptor")
public class Receiver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_receptor")
    private long receiverId;

    @Column(name = "nombre", length = 50, nullable = false)
    private String name;

    @Column(name = "primer_apellido", length = 50, nullable = false)
    private String lastName;

    @Column(name = "segundo_apellido", length = 50)
    private String twoLastName;

    @Column(name = "telefono", length = 10)
    private String phone;

    @Column(name = "email", length = 200)
    private String email;

    @Column(name = "grado_academico", length = 30)
    private String academicGrade;

    @OneToMany(mappedBy = "receiver")
    @Builder.Default
    private List<Proof> proofs = new ArrayList<>();
}
