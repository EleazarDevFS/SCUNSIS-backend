package com.unsis.scunsis_backend.dto.response.receiver;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReceiverResponse {
    private Long receiverId;
    private String name;
    private String lastName;
    private String twoLastName;
    private String phone;
    private String email;
    private String academicGrade;
}
