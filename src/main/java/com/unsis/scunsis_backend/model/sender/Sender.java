package com.unsis.scunsis_backend.model.sender;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data

public class Sender {

    @Id
    @GeneratedValue
    @Column(name = "id_emisor")
    private long senderId;

    @Column(name = "nombre", length = 50, nullable = false)
    private String name;

    @Column(name = "sede_campus", length = 50)
    private String campus;

}
