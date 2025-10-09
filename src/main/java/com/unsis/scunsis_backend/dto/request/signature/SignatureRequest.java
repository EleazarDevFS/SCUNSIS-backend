package com.unsis.scunsis_backend.dto.request.signature;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignatureRequest {

    private String name; 

    private String lastName; 

    private String twoLastName;

    private String position;

    private String academicGrade;
}
