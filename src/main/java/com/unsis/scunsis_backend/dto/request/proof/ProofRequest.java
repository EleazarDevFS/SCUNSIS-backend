package com.unsis.scunsis_backend.dto.request.proof;

import java.sql.Date;

import com.unsis.scunsis_backend.model.activity.Activity;
import com.unsis.scunsis_backend.model.enums.EProofType;
import com.unsis.scunsis_backend.model.event.Event;
import com.unsis.scunsis_backend.model.receiver.Receiver;
import com.unsis.scunsis_backend.model.sender.Sender;
import com.unsis.scunsis_backend.model.signature.Signature;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProofRequest {
    
    private String folio;

    private Signature signature;

    private Sender sender;

    private Receiver receiver;    

    private Activity activity;

    private Event event;

    private EProofType proofType;

    private Date date;
}
