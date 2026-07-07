package com.unsis.scunsis_backend.dto.request.proof;

import java.sql.Date;

import com.unsis.scunsis_backend.model.activity.Activity;
import com.unsis.scunsis_backend.model.enums.EProofType;
import com.unsis.scunsis_backend.model.event.Event;
import com.unsis.scunsis_backend.model.receiver.Receiver;
import com.unsis.scunsis_backend.model.sender.Sender;

import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProofRequest {
    @NonNull
    @NotBlank
    private Sender sender;
    @NonNull
    @NotBlank
    private Receiver receiver;    
    @NotBlank
    @NonNull
    private Activity activity;
    @NotBlank
    @NonNull
    private Event event;
    
    private EProofType proofType;
    @NonNull
    @NotBlank
    private Date date;
}
