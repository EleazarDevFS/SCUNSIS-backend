package com.unsis.scunsis_backend.dto.response.proof;

import java.time.LocalDate;

import com.unsis.scunsis_backend.model.enums.EParticipationRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProofResponse {
    private String folio;
    private Long senderId;
    private String senderName;
    private Long receiverId;
    private String receiverFullName;
    private String receiverEmail;
    private Long activityId;
    private String activityName;
    private Long eventId;
    private String eventName;
    private EParticipationRole role;
    private LocalDate date;
}
