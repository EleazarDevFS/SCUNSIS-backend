package com.unsis.scunsis_backend.dto.request.receiver;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReceiverRequest {

    private String name;

    private String lastName;

    private String twoLastName;

    private String phone;

    private String email;

    private String academicGrade;
}
