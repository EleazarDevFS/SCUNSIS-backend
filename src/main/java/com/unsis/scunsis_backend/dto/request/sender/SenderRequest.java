package com.unsis.scunsis_backend.dto.request.sender;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SenderRequest {
    private String name;
    private String campus;
}
