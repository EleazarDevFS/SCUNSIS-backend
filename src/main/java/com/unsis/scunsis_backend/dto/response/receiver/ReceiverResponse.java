package com.unsis.scunsis_backend.dto.response.receiver;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReceiverResponse {

    private String name;

    private String lastName;

    private String twoLastName;

    private String phone;

    private String email;

    private String academicGrade;
}
