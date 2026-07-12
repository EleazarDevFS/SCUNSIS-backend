package com.unsis.scunsis_backend.dto.request.proof;

import com.unsis.scunsis_backend.model.enums.EParticipationRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProofRequest {
    private Long senderId;
    private Long receiverId;
    private Long activityId;
    private Long eventId;
    private EParticipationRole role;
}
