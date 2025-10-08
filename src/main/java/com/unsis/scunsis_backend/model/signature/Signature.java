package com.unsis.scunsis_backend.model.signature;

import java.util.ArrayList;
import java.util.List;

import com.unsis.scunsis_backend.model.proof.Proof;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "firma")
@NoArgsConstructor
@AllArgsConstructor
public class Signature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_firma")
    private long id;

    @Column(name = "nombre")
    private String name; 

    @Column(name = "primer_apellido", length = 50)
    private String lastName; 

    @Column(name = "segundo_apellido", length = 50)
    private String twoLastName;

    @Column(name = "cargo", length = 50)
    private String position;
    
    @Column(name = "grado", length = 30)
    private String academicGrade;

    @OneToMany(mappedBy = "signature")
    private List<Proof> proofs = new ArrayList<>();
}
