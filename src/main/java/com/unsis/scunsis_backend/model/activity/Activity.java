package com.unsis.scunsis_backend.model.activity;

import java.sql.Date;

import jakarta.persistence.*;

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
    private long activityId;

    // Id de evento. hay que hacer la fk y verificar su relación con Event
    //@ManyToOne
    //private Event eventId;

    @Column(name = "nombre_actividad", length = 50, nullable = false)
    private String activityName;

    @Column(name = "objetivo_actividad", length = 200, nullable = false)
    private String activityDescription;

    @Column(name = "fecha_inicio", nullable = false)
    private Date startDate;

    @Column(name = "fecha_fin")
    private Date endDate;

    @Column(name = "lugar_actividad", length = 200)
    private String activityPlace;
}
