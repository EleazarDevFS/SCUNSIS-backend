package com.unsis.scunsis_backend.model.sender;

import java.util.ArrayList;
import java.util.List;

import com.unsis.scunsis_backend.model.proof.Proof;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "emisor")
public class Sender {

    @Id
    @GeneratedValue
    @Column(name = "id_emisor")
    private long senderId;

    @Column(name = "nombre", length = 50, nullable = false)
    private String name;

    @Column(name = "sede_campus", length = 50)
    private String campus;

    @OneToMany(mappedBy = "sender")
    private List<Proof> proofs = new ArrayList<>();

}
