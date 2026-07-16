package com.unsis.scunsis_backend.dto.request.receiver;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReceiverRequest {
    @JsonAlias("nombre")
    private String name;
    @JsonAlias("primer_apellido")
    private String lastName;
    @JsonAlias("segundo_apellido")
    private String twoLastName;
    @JsonAlias({"telefono", "phone"})
    private String phone;
    private String email;
    @JsonAlias("grado_academico")
    private String academicGrade;
}
