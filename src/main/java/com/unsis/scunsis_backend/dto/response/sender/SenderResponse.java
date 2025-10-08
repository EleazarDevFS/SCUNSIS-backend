package com.unsis.scunsis_backend.dto.response.sender;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SenderResponse {

    private String name;

    private String campus;
}
