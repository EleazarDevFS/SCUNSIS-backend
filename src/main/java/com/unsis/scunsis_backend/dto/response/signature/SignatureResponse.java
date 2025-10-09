package com.unsis.scunsis_backend.dto.response.signature;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignatureResponse {

    private String name; 

    private String lastName; 

    private String twoLastName;

    private String position;

    private String academicGrade;
}
