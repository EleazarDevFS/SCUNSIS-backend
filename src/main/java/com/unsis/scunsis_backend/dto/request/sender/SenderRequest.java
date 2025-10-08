package com.unsis.scunsis_backend.dto.request.sender;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SenderRequest {

    private String name;

    private String campus;
}
