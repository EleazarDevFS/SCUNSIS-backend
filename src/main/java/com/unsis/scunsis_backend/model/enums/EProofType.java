package com.unsis.scunsis_backend.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EProofType {
    CONFERENCE("Jornada"), 
    CONGRESS("Congreso"), 
    PRESENTATION("Ponencia"), 
    RECOGNITION("Reconocimiento"), 
    OTHER("Otro");
    private String type;
}
