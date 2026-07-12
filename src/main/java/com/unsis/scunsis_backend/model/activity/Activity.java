package com.unsis.scunsis_backend.model.activity;

import java.time.LocalDate;

import com.unsis.scunsis_backend.model.event.Event;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "actividad")
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_actividad")
    private Long activityId;

    @ManyToOne
    @JoinColumn(name = "fk_evento")
    private Event event;

    @Column(name = "nombre_actividad", length = 50, nullable = false)
    private String activityName;

    @Column(name = "objetivo_actividad", length = 200, nullable = false)
    private String activityDescription;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate startDate;

    @Column(name = "fecha_fin")
    private LocalDate endDate;

    @Column(name = "lugar_actividad", length = 200)
    private String activityPlace;

}
