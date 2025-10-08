package com.unsis.scunsis_backend.model.proof;

import java.sql.Date;

import com.unsis.scunsis_backend.model.activity.Activity;
import com.unsis.scunsis_backend.model.enums.EProofType;
import com.unsis.scunsis_backend.model.event.Event;
import com.unsis.scunsis_backend.model.receiver.Receiver;
import com.unsis.scunsis_backend.model.sender.Sender;
import com.unsis.scunsis_backend.model.signature.Signature;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@Table(name = "Constancia")
@NoArgsConstructor
@AllArgsConstructor
public class Proof {

    @Id
    private String folio;

    @ManyToOne
    @JoinColumn(name = "fk_id_firma")
    private Signature signature;

    @ManyToOne
    @JoinColumn(name = "fk_id_emisor")
    private Sender sender;

    @ManyToOne
    @JoinColumn(name = "fk_id_receptor")
    private Receiver receiver;    

    @OneToOne
    @JoinColumn(name = "fk_id_actividad")
    private Activity activity;

    @ManyToOne
    @JoinColumn(name = "fk_id_event")
    private Event event;
    // Cambiar a @Enumerated(EnumType.STRING)
    @Column(name = "tipo_constancia")
    private EProofType proofType;

    @Column(name = "fecha", nullable = false)
    private Date date;

}
