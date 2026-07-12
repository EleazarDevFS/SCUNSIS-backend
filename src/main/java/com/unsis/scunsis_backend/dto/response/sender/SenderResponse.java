package com.unsis.scunsis_backend.dto.response.sender;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SenderResponse {
    private Long senderId;
    private String name;
    private String campus;
}
