package com.unsis.scunsis_backend.model.receiver;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "receptor")
public class Receiver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_receptor")
    private Long receiverId;

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

}
