package com.unsis.scunsis_backend.model.proof;

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

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@Table(name = "constancia_archivo")
@NoArgsConstructor
@AllArgsConstructor
public class ProofFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_archivo")
    private Long id;

    @Column(name = "folio", length = 20, nullable = false, unique = true)
    private String folio;

    @Column(name = "ruta_pdf", length = 500)
    private String rutaPdf;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
}
