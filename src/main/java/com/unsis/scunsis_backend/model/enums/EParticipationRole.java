package com.unsis.scunsis_backend.model.enums;

public enum EParticipationRole {
    PONENTE("PON", "Ponente"),
    PARTICIPANTE("PAR", "Participante"),
    ORGANIZADOR("ORG", "Organizador"),
    RECONOCIMIENTO("REC", "Reconocimiento");

    private final String code;
    private final String displayName;

    EParticipationRole(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

    public String getCode() {
        return code;
    }

    public String getDisplayName() {
        return displayName;
    }
}
