package com.unsis.scunsis_backend.model.event;

import java.time.LocalDate;

import com.unsis.scunsis_backend.model.enums.EEventType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "evento")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evento")
    private Long eventId;

    @Column(name = "tipo")
    @Enumerated(EnumType.STRING)
    private EEventType eventType;

    @Column(name = "nombre_evento", length = 200, nullable = false)
    private String eventName;

    @Column(name = "fecha_inicio")
    private LocalDate startDate;

    @Column(name = "fecha_fin")
    private LocalDate endDate;

    @Column(name = "lugar_evento", length = 200)
    private String eventPlace;

    @Column(name = "objetivo_evento")
    private String eventDescription;

}
