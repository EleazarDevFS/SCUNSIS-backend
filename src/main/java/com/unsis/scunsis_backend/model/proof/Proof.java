package com.unsis.scunsis_backend.model.proof;

import java.time.LocalDate;

import com.unsis.scunsis_backend.model.activity.Activity;
import com.unsis.scunsis_backend.model.enums.EParticipationRole;
import com.unsis.scunsis_backend.model.event.Event;
import com.unsis.scunsis_backend.model.receiver.Receiver;
import com.unsis.scunsis_backend.model.sender.Sender;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "constancia")
@NoArgsConstructor
@AllArgsConstructor
public class Proof {

    @Id
    @Column(name = "folio", length = 20)
    private String folio;

    @ManyToOne
    @JoinColumn(name = "fk_id_emisor")
    private Sender sender;

    @ManyToOne
    @JoinColumn(name = "fk_id_receptor")
    private Receiver receiver;

    @ManyToOne
    @JoinColumn(name = "fk_id_actividad")
    private Activity activity;

    @ManyToOne
    @JoinColumn(name = "fk_id_evento")
    private Event event;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol", length = 20)
    private EParticipationRole role;

    @Column(name = "fecha", nullable = false)
    private LocalDate date;

}
